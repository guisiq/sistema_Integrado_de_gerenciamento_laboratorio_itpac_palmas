package br.com.itpacpalmas.api_sig_lab_itpac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
  
    @Query(value = "select * from permissao c  ORDER BY unaccent(upper(c.nome)) ASC ", nativeQuery = true)
    List<Permissao> buscarTodos();
}
