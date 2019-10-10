package br.com.dbccompany;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Contribuicao;
import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Service.CampanhaService;
import br.com.dbccompany.Service.ContribuicaoService;
import br.com.dbccompany.Service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ContribuicaoTest extends ColaboreApplicationTests{
    @Autowired
    private ContribuicaoService contribuicaoService;

    @Autowired
    private CampanhaService campanhaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    ApplicationContext context;

    @Transactional
    @Test
    public void testaContribuicaoSalva(){
        Usuario usuario = new Usuario();
        usuario.setEmail("dani@dani");
        usuario.setNome("Daniela");
        usuario.setSenha("dani");
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        Campanha campanha = new Campanha();
        campanha.setUsuario(usuarioSalvo);
        campanha.setTitulo("Animais");
        campanha.setDescricao("Arrecadacao para animais");
        campanha.setMetaArrecadacao(100.2);
        campanha.setIsConclusaoAutomatica(true);
        Campanha campanhaSalva = campanhaService.salvar(campanha);

        Contribuicao contribuicao = new Contribuicao();
        contribuicao.setCampanha(campanhaSalva);
        contribuicao.setUsuario(usuarioSalvo);
        contribuicao.setValor(100d);
        Contribuicao resultado = contribuicaoService.salvar(contribuicao);
        assertThat(resultado.getValor()).isEqualTo(100d);
    }

    @Transactional
    @Test
    public void testaSeContribuicaoAlteraValorArrecadadoDaCampanha(){
        Usuario usuario = new Usuario();
        usuario.setEmail("dani@dani");
        usuario.setNome("Daniela");
        usuario.setSenha("dani");
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        Campanha campanha = new Campanha();
        campanha.setUsuario(usuarioSalvo);
        campanha.setTitulo("Animais");
        campanha.setDescricao("Arrecadacao para animais");
        campanha.setMetaArrecadacao(100.2);
        campanha.setIsConclusaoAutomatica(true);
        Campanha campanhaSalva = campanhaService.salvar(campanha);

        Contribuicao contribuicao = new Contribuicao();
        contribuicao.setCampanha(campanhaSalva);
        contribuicao.setUsuario(usuarioSalvo);
        contribuicao.setValor(50d);
        Contribuicao resultado = contribuicaoService.salvar(contribuicao);
        assertThat(resultado.getCampanha().getValorArrecadado()).isEqualTo(50d);
    }

    @Transactional
    @Test
    public void testaSeDeletarContribuicaoAlteraValorArrecadadoDaCampanha(){
        Usuario usuario = new Usuario();
        usuario.setEmail("dani@dani");
        usuario.setNome("Daniela");
        usuario.setSenha("dani");
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        Campanha campanha = new Campanha();
        campanha.setUsuario(usuarioSalvo);
        campanha.setTitulo("Animais");
        campanha.setDescricao("Arrecadacao para animais");
        campanha.setMetaArrecadacao(100.2);
        campanha.setValorArrecadado(30d);
        campanha.setIsConclusaoAutomatica(true);
        Campanha campanhaSalva = campanhaService.salvar(campanha);

        Contribuicao contribuicao = new Contribuicao();
        contribuicao.setCampanha(campanhaSalva);
        contribuicao.setUsuario(usuarioSalvo);
        contribuicao.setValor(50d);
        Contribuicao contribuicaoSalva = contribuicaoService.salvar(contribuicao);
        assertThat(campanhaSalva.getValorArrecadado()).isEqualTo(80d);
        contribuicaoService.deletarContribuicao(contribuicaoSalva.getId());
        assertThat(campanhaSalva.getValorArrecadado()).isEqualTo(30d);
    }
}