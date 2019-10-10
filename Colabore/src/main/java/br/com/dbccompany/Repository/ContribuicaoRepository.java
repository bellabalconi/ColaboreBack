package br.com.dbccompany.Repository;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Contribuicao;
import br.com.dbccompany.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContribuicaoRepository extends CrudRepository<Contribuicao, Long> {
    List<Contribuicao> findByUsuario(Usuario usuario);
    List<Contribuicao> findByCampanha(Campanha campanha);

}
