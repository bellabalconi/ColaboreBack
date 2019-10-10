package br.com.dbccompany.Repository;

import br.com.dbccompany.Entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByNome(String nome);
}
