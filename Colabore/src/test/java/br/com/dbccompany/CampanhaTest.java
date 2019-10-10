package br.com.dbccompany;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Service.CampanhaService;
import br.com.dbccompany.Service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CampanhaTest extends ColaboreApplicationTests {
    @Autowired
    ApplicationContext context;

    @Autowired
    private CampanhaService campanhaService;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    @Test
    public void testaSalvarCampanha(){
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
        assertThat(campanhaSalva.getTitulo()).isEqualTo("Animais");
    }


    @Transactional
    @Test
    public void testaSeIsCriadorDeUsuarioPassouParaTrueAoSalvarCampanha(){
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
        Usuario criador = campanhaSalva.getUsuario();
        assertThat(criador.getIsCriador()).isEqualTo(true);
    }

    @Transactional
    @Test
    //Se ValorArrecadado >= MetaArrecadacao setar isConcluida para true
    public void testaSeStatusConcluidoEhAtualizado(){
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

        campanhaSalva.setValorArrecadado(101d);
        Campanha campanhaEditada = campanhaService.editarDados(campanhaSalva.getId(), campanhaSalva);
        assertThat(campanhaEditada.getisConcluida()).isEqualTo(true);
    }

    @Transactional
    @Test
    public void testaSeSalvarCampanhaSemDataExpectativaDataSetaData(){
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
        assertThat(campanhaSalva.getExpectativaData()).isNotNull();
    }

    @Transactional
    @Test
    public void testaListagemFiltrada(){
        Usuario usuario = new Usuario();
        usuario.setEmail("dani@dani");
        usuario.setNome("Daniela");
        usuario.setSenha("dani");
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("aa@d");
        usuario2.setNome("Daniela");
        usuario2.setSenha("dani");
        Usuario usuarioSalvo2 = usuarioService.salvar(usuario2);

        Campanha campanha = new Campanha();
        campanha.setUsuario(usuarioSalvo);
        campanha.setTitulo("Animais");
        campanha.setDescricao("Arrecadacao para animais");
        campanha.setMetaArrecadacao(100.2);
        campanha.setIsConclusaoAutomatica(true);
        Campanha campanhaSalva = campanhaService.salvar(campanha);

        Campanha campanha2 = new Campanha();
        campanha2.setUsuario(usuarioSalvo2);
        campanha2.setTitulo("Animais2");
        campanha2.setDescricao("Arrecadacao para animais2");
        campanha2.setMetaArrecadacao(100.2);
        campanha2.setIsConclusaoAutomatica(true);
        campanhaService.salvar(campanha2);
        Usuario usuarioCampanha = campanhaSalva.getUsuario();
        List<Campanha> resultado = campanhaService.buscarCampanhasPorCriador(usuarioCampanha);
        assertThat(resultado.size()).isEqualTo(1);
    }

}
