package br.com.dbccompany.Service;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Contribuicao;
import br.com.dbccompany.Entity.Tag;
import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Properties.StorageProperties;
import br.com.dbccompany.Repository.CampanhaRepository;
import br.com.dbccompany.Repository.ContribuicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CampanhaService {
    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private ContribuicaoRepository contribuicaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TagService tagService;

    public String setReais(double valor){
        NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return df.format(valor);
    }

    private Usuario validacaoUsuario(long id){
        Usuario usuario = usuarioService.buscarPorId(id).get();
        //TODO verificar se usuario n existe, gerar erro
        if(!usuario.getIsCriador()){ usuario.setIsCriador(true); }
        return usuario;
    }
    //converte de String para LocalDate
    private LocalDate conversaoData(String data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formatter);
    }

    @Transactional(rollbackFor = Exception.class)
    public Campanha salvar(Campanha campanha) {
        //Seta usuario da campanha
        campanha.setUsuario(validacaoUsuario(campanha.getUsuario().getId()));

        campanha.setMetaArrecadacaoString( setReais( campanha.getMetaArrecadacao() ) );
        campanha.setValorArrecadadoString( setReais(0) );

        LocalDate hoje = LocalDate.now();
        // se expectativa de data nao foi inserida, seta para 3 meses a partir da data em que foi salvo
        if(campanha.getExpectativaDataString() == null || campanha.getExpectativaDataString().equalsIgnoreCase(" ")){
            campanha.setExpectativaData(hoje.plusMonths(3));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            campanha.setExpectativaDataString(campanha.getExpectativaData().format(formatter));
        } else {
            campanha.setExpectativaData(conversaoData(campanha.getExpectativaDataString()));

        }
        campanha.setUltimaModificacao(hoje);

        //salva as tags
        if(campanha.getTags().size() > 0){
            List<Tag> campanhasDaTag = new ArrayList<>();
            campanhasDaTag.addAll(tagService.salvar(campanha.getTags()));
            campanha.setTags(campanhasDaTag);
        }

        Boolean conclusaAutomatica = campanha.getisConclusaoAutomatica();
        campanha.setIsConclusaoAutomatica(conclusaAutomatica);
        campanha.setValorArrecadado(0);
        return campanhaRepository.save(campanha);
    }

    public Optional<Campanha> buscarCampanhaPorId(long id) {
        return campanhaRepository.findById(id);
    }

    public List<Campanha> buscarCampanhasPorCriador(Usuario usuario) {
        Usuario usuarioBanco = usuarioService.buscarPorId(usuario.getId()).get();
        return campanhaRepository.findByUsuario(usuarioBanco);
    }

    public List<Campanha> buscarCampanhasPorTags(List<Tag> tags) {
        return campanhaRepository.findByTags(tags);
    }

    public List<Campanha> buscarCampanhasPorConclusao(boolean isConcluida) {
        return campanhaRepository.findByisConcluida(isConcluida);
    }

    public List<Campanha> buscarCampanhasPorContribuinte (Usuario usuario){
        //busca as campanhas em que o usuario passado por parametro contribuiu
        List<Contribuicao> contribuicoes = contribuicaoRepository.findByUsuario(usuario);
        List<Campanha> campanhas = new ArrayList<>();
        for(Contribuicao contribuicao : contribuicoes){
            campanhas.add(contribuicao.getCampanha());
        }
        return campanhas;
    }

    @Transactional(rollbackFor = Exception.class)
    public Campanha editarDados(long id, Campanha campanha) {
        campanha.setUsuario(validacaoUsuario(campanha.getUsuario().getId()));

        campanha.setMetaArrecadacaoString( setReais( campanha.getMetaArrecadacao() ) );
        campanha.setValorArrecadadoString( setReais( campanha.getValorArrecadado() ) );

        campanha.setId(id);
        //salva a data atual como ultimaModificacao
        LocalDate hoje = LocalDate.now();
        campanha.setUltimaModificacao(hoje);

        //verifica se o valor arrecadado ja atingiu a meta, se sim seta o atributo isConcluida para true
        if(campanha.getValorArrecadado() >= campanha.getMetaArrecadacao()){
            campanha.setIsConcluida(true);
        }
        if(campanha.getExpectativaDataString() != null){
            campanha.setExpectativaData(conversaoData(campanha.getExpectativaDataString()));
        }

        return campanhaRepository.save(campanha);
    }

    public List<Campanha> allCampanhas(){
        return (List<Campanha>) campanhaRepository.findAll();
    }

    public void deletarCampanha(long id){
        campanhaRepository.deleteById(id);
    }

    //IMAGEM
    private final Path rootLocation;
    @Autowired
    public CampanhaService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) { }
        return this.rootLocation.toAbsolutePath().toString().replace("./", "") + "/" + file.getOriginalFilename();
    }

//    public Path load(String filename) {
//        return rootLocation.resolve(filename);
//    }

    public Resource loadAsResource(String url) {
        try {
            Path file = Paths.get(url);
            Resource resource = new UrlResource(file.toUri());
            return resource;
        }
        catch (MalformedURLException e) {
        }
        return null;
    }
}