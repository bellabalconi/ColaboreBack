package br.com.dbccompany.Security;

import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service("userDetailsService")
public class UserDetailsImplementacao implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail( email );

        if( usuario == null ){
            throw new UsernameNotFoundException( email + "não registrado! ");
        }

        Set<GrantedAuthority> permissoes = Stream
                .of( new SimpleGrantedAuthority( usuario.getNome() ) )
                .collect( toSet() );

        return new User( usuario.getEmail(), usuario.getSenha(), permissoes);
    }
}

