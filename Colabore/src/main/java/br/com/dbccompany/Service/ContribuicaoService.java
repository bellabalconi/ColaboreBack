package br.com.dbccompany.Service;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Contribuicao;
import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Repository.ContribuicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContribuicaoService {
    @Autowired
    private ContribuicaoRepository contribuicaoRepository;

    @Autowired
    private CampanhaService campanhaService;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional(rollbackFor = Exception.class)
    public Contribuicao salvar(Contribuicao contribuicao) {
        //adiciona o valor da contribuicao ao valor arrecadado da campanha
        Campanha campanha = campanhaService.buscarCampanhaPorId(contribuicao.getCampanha().getId()).get();
        campanha.setValorArrecadado(campanha.getValorArrecadado() + contribuicao.getValor());
        Campanha campanhaSalva = campanhaService.editarDados(campanha.getId(), campanha);
        contribuicao.setCampanha(campanhaSalva);
        Usuario usuario = usuarioService.buscarPorId(contribuicao.getUsuario().getId()).get();
        contribuicao.setUsuario(usuario);
        return contribuicaoRepository.save(contribuicao);
    }

    public Optional<Contribuicao> buscarContribuicaoPorId(long id) {
        return contribuicaoRepository.findById(id);
    }

    public List<Contribuicao> buscarContribuicoesPorContribuinte(Usuario usuario) {
        return contribuicaoRepository.findByUsuario(usuario);
    }

    public List<Contribuicao> buscarContribuicoesPorCampanha(Campanha campanha) {
        return contribuicaoRepository.findByCampanha(campanha);
    }

    @Transactional(rollbackFor = Exception.class)
    public Contribuicao editarDados(long id, Contribuicao contribuicao) {
        contribuicao.setId(id);
        return contribuicaoRepository.save(contribuicao);
    }

    public List<Contribuicao> allContribuicoes(){
        return (List<Contribuicao>) contribuicaoRepository.findAll();
    }

    public void deletarContribuicao(long id){
        //remove o valor da contribuicao do valor arrecadado da campanha
        Contribuicao contribuicao = contribuicaoRepository.findById(id).get();
        Campanha campanha = campanhaService.buscarCampanhaPorId(contribuicao.getCampanha().getId()).get();
        campanha.setValorArrecadado(campanha.getValorArrecadado() - contribuicao.getValor());
        campanhaService.editarDados(campanha.getId(), campanha);
        contribuicaoRepository.deleteById(id);
    }
}
