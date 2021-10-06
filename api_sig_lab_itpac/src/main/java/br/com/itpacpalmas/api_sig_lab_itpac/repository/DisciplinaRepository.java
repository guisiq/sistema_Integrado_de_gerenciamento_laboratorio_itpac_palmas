package br.com.itpacpalmas.api_sig_lab_itpac.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    @Query(value = "select * from aluno c  ORDER BY unaccent(upper(c.nome)) ASC ", nativeQuery = true)
    List<Aluno> buscarNome();
}
