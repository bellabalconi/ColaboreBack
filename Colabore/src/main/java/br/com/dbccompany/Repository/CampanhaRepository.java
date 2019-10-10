package br.com.dbccompany.Repository;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Tag;
import br.com.dbccompany.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CampanhaRepository extends CrudRepository<Campanha, Long> {
    List<Campanha> findByUsuario(Usuario usuario);
    List<Campanha> findByTags(List<Tag> tags);
    List<Campanha> findByisConcluida(boolean concluida);
    Campanha findByTitulo(String titulo);
}
