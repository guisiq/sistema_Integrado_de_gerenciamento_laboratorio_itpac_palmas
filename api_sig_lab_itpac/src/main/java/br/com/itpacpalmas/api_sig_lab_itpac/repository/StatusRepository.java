package br.com.itpacpalmas.api_sig_lab_itpac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

    @Query(value = "select * from status c  ORDER BY unaccent(upper(c.descricao)) ASC ", nativeQuery = true)
    List<Status> buscarTodos();

}
