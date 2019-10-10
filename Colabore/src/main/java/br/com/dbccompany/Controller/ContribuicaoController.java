package br.com.dbccompany.Controller;

import br.com.dbccompany.Entity.Contribuicao;
import br.com.dbccompany.Service.CampanhaService;
import br.com.dbccompany.Service.ContribuicaoService;
import br.com.dbccompany.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/contribuicao")
public class ContribuicaoController {

    @Autowired
    ContribuicaoService contribuicaoService;

    @Autowired
    CampanhaService campanhaService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/novo")
    @ResponseBody
    public Contribuicao salvarNovo(@RequestBody Contribuicao contribuicao) {
        return contribuicaoService.salvar(contribuicao);
    }

    @GetMapping( value = "/buscarPorId/{id}" )
    @ResponseBody
    public Contribuicao buscarContribuicaoPorId(@PathVariable long id ) {
        return contribuicaoService.buscarContribuicaoPorId(id).get();
    }

    @GetMapping( value = "/buscarPorCampanha/{campanhaId}" )
    @ResponseBody
    public List<Contribuicao> buscarContribuicoesPorCampanha(@PathVariable long campanhaId ) {
        return contribuicaoService.buscarContribuicoesPorCampanha(campanhaService.buscarCampanhaPorId(campanhaId).get());
    }

    @GetMapping( value = "/buscarPorContribuinte/{contribuinteId}" )
    @ResponseBody
    public List<Contribuicao> buscarContribuicoesPorContribuinte(@PathVariable long contribuinteId ) {
        return contribuicaoService.buscarContribuicoesPorContribuinte(usuarioService.buscarPorId(contribuinteId).get());
    }

    @DeleteMapping(value = "/deletar/{id}")
    @ResponseBody
    public void deletarContribuicaoPorId(@PathVariable long id){
        contribuicaoService.deletarContribuicao(id);
    }
}
