package br.com.dbccompany;

import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UsuarioTest extends ColaboreApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    ApplicationContext context;

    @Test
    public void testarSenhaCriptograda() {
        Usuario usuario = new Usuario();
        usuario.setNome("Paula");
        usuario.setEmail("teste@email");
        usuario.setSenha("xavrauuuuu");
        usuarioService.salvar(usuario);
        Usuario result = usuarioService.buscarPorEmail("teste@email");
        boolean resultado = !(result.getSenha().equals("xavrauuuuu"));
        assertThat(resultado).isEqualTo(true);
    }

    @Transactional
    @Test
    public void testarSeOUsuarioEstaSendoCriadoEComIsCriadorFalse() {
        Usuario usuario = new Usuario();
        usuario.setNome("Rogerio");
        usuario.setEmail("rogerio@dbccompany.com.br");
        usuario.setSenha("xavrauuuu");
        Usuario usuarioBanco = usuarioService.salvar(usuario);

        assertThat(usuarioBanco.getIsCriador()).isEqualTo(false);
        assertThat(usuarioBanco.getNome()).isEqualTo(usuario.getNome());
    }
    @Transactional
    @Test
    //testa o metodo editarValorCriadorDoUsuario(long id, boolean status)
    public void testaSeIsCriadorMudaComAEdicao(){
        Usuario usuario = new Usuario();
        usuario.setNome("Rogerio");
        usuario.setEmail("rogerio@dbccompany.com.br");
        usuario.setSenha("xavrauuuu");
        Usuario usuarioBanco = usuarioService.salvar( usuario );
        Usuario usuarioEditado = usuarioService.editarValorCriadorDoUsuario(usuarioBanco.getId(), true);
        assertThat(usuarioEditado.getIsCriador()).isEqualTo(true);

        Usuario usuarioEditado2 = usuarioService.editarValorCriadorDoUsuario(usuarioEditado.getId(), false);
        assertThat(usuarioEditado2.getIsCriador()).isEqualTo(false);

    }
}