package br.com.dbccompany.Controller;

import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping( value = "/" )
    @ResponseBody
    public List<Usuario> lstUsuarios() {
        return usuarioService.buscarAll();
    }

    @PostMapping( value = "/novo" )
    @ResponseBody
    public Usuario buscarUsuario( @RequestBody Usuario usuario ) {
        return usuarioService.salvar( usuario );
    }

    @GetMapping( value = "/{id}" )
    @ResponseBody
    public Optional<Usuario> buscarUsuarioPorId( @PathVariable long id ) {
        return usuarioService.buscarPorId( id );
    }

    @PutMapping( value = "/editar/{id}" )
    @ResponseBody
    public Usuario editarUsuario( @PathVariable long id, @RequestBody Usuario usuario ) {
        return usuarioService.editarUsuario( id, usuario );
    }

    @GetMapping( value = "/buscarPorEmail/{email}" )
    @ResponseBody
    public Usuario buscarPorEmail( @PathVariable String email ) {
        return usuarioService.buscarPorEmail( email );
    }

    @PutMapping( value = "/editarValorCriador/{id}" )
    @ResponseBody
    public Usuario editarValorCriadorDoUsuario( @PathVariable long id, @RequestBody boolean status) {
        return usuarioService.editarValorCriadorDoUsuario( id, status );
    }

}