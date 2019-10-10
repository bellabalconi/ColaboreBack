package br.com.dbccompany.Controller;

import br.com.dbccompany.DTO.CampanhaDTO;
import br.com.dbccompany.DTO.UsuarioDTO;
import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Imagem;
import br.com.dbccompany.Entity.Tag;
import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Service.CampanhaService;
import br.com.dbccompany.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/campanha")
public class CampanhaController {
    @Autowired
    CampanhaService campanhaService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/novo")
    @ResponseBody
    public Campanha salvarNovo(@RequestBody Campanha campanha) {
        return campanhaService.salvar(campanha);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<CampanhaDTO> listarTodos() {
        List<CampanhaDTO> campanhaDTOS = new ArrayList<>();
        for(Campanha campanha : campanhaService.allCampanhas()){
            Usuario usu = campanha.getUsuario();
            UsuarioDTO usDto = new UsuarioDTO(); usDto.setId(usu.getId()); usDto.setEmail(usu.getEmail()); usDto.setNome(usu.getNome()); usDto.setSenha(usu.getSenha());usDto.setIsCriador(usu.getIsCriador());
            List<Long> idsCampanhas = new ArrayList<>();
            for(Campanha camp : usu.getCampanhas()){
                idsCampanhas.add(camp.getId());
            }
            usDto.setCampanhas(idsCampanhas);
            CampanhaDTO campDto = new CampanhaDTO(); campDto.setId(campanha.getId()); campDto.setTitulo(campanha.getTitulo()); campDto.setMetaArrecadacao(campanha.getMetaArrecadacao()); campDto.setMetaArrecadacaoString(campanha.getMetaArrecadacaoString()); campDto.setExpectativaData(campanha.getExpectativaData()); campDto.setExpectativaDataString(campanha.getExpectativaDataString()); campDto.setDescricao(campanha.getDescricao()); campDto.setIConclusaoAutomatica(campanha.getisConclusaoAutomatica()); campDto.setIsConcluida(campanha.getisConcluida()); campDto.setTags(campanha.getTags()); campDto.setUltimaModificacao(campanha.getUltimaModificacao()); campDto.setUsuario(usDto); campDto.setContribuicoes(campanha.getContribuicoes());
            campanhaDTOS.add(campDto);
        }
        return campanhaDTOS;
    }

    @GetMapping( value = "/buscarPorId/{id}" )
    @ResponseBody
    public Optional<Campanha> buscarCampanhaPorId(@PathVariable long id ) {
        return campanhaService.buscarCampanhaPorId( id );
    }

    @GetMapping( value = "/buscarPorStatus/{status}" )
    @ResponseBody
    public List<Campanha> buscarCampanhaPorConcluidaOuNao(@PathVariable boolean status ) {
        return campanhaService.buscarCampanhasPorConclusao(status);
    }

    @GetMapping( value = "/buscarPorCriador/{criadorId}" )
    @ResponseBody
    public List<Campanha> buscarCampanhaPorCriador(@PathVariable long criadorId ) {
        Usuario criador = usuarioService.buscarPorId(criadorId).get();
        return campanhaService.buscarCampanhasPorCriador(criador);
    }

    @PostMapping(value = "/buscarPorTags")
    @ResponseBody
    public List<Campanha> buscarCampanhaPorTags(@RequestBody List<Tag> tags) { return campanhaService.buscarCampanhasPorTags(tags); }

    @GetMapping( value = "/buscarPorContribuinte/{contribuinteId}" )
    @ResponseBody
    public List<CampanhaDTO> buscarCampanhaPorContribuinte(@PathVariable long contribuinteId ) {
        Usuario contribuinte = usuarioService.buscarPorId(contribuinteId).get();
        List<CampanhaDTO> campanhaDTOS = new ArrayList<>();
        for(Campanha campanha : campanhaService.buscarCampanhasPorContribuinte(contribuinte)){
            Usuario usu = campanha.getUsuario();
            UsuarioDTO usDto = new UsuarioDTO(); usDto.setId(usu.getId()); usDto.setEmail(usu.getEmail()); usDto.setNome(usu.getNome()); usDto.setSenha(usu.getSenha());usDto.setIsCriador(usu.getIsCriador());
            List<Long> idsCampanhas = new ArrayList<>();
            for(Campanha camp : usu.getCampanhas()){
                idsCampanhas.add(camp.getId());
            }
            usDto.setCampanhas(idsCampanhas);
            CampanhaDTO campDto = new CampanhaDTO(); campDto.setId(campanha.getId()); campDto.setTitulo(campanha.getTitulo()); campDto.setMetaArrecadacao(campanha.getMetaArrecadacao()); campDto.setMetaArrecadacaoString(campanha.getMetaArrecadacaoString()); campDto.setExpectativaData(campanha.getExpectativaData()); campDto.setExpectativaDataString(campanha.getExpectativaDataString()); campDto.setDescricao(campanha.getDescricao()); campDto.setIConclusaoAutomatica(campanha.getisConclusaoAutomatica()); campDto.setIsConcluida(campanha.getisConcluida()); campDto.setTags(campanha.getTags()); campDto.setUltimaModificacao(campanha.getUltimaModificacao()); campDto.setUsuario(usDto); campDto.setContribuicoes(campanha.getContribuicoes());
            campanhaDTOS.add(campDto);
        }
        return campanhaDTOS;
    }

    @PutMapping( value = "/editar/{id}" )
    @ResponseBody
    public Campanha editarCampanha(@PathVariable long id, @RequestBody Campanha campanha) {
        return campanhaService.editarDados(id, campanha);
    }

    @DeleteMapping(value = "/deletar/{id}")
    @ResponseBody
    public void deletarCampanhaPorId(@PathVariable long id){
        campanhaService.deletarCampanha(id);
    }

    @RequestMapping(value = "/salvarImagem/{id}", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable  long id) {
        Campanha campanha = campanhaService.buscarCampanhaPorId(id).get();
        String urlImagem = campanhaService.store(file);
        campanha.setImagem(urlImagem);
        campanhaService.editarDados(id, campanha);
        return urlImagem;
    }

    @PostMapping("/files")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@RequestBody Imagem filename) {
        Resource file = campanhaService.loadAsResource(filename.getUrl());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
