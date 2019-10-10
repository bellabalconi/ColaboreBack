package br.com.dbccompany.Repository;

import br.com.dbccompany.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
