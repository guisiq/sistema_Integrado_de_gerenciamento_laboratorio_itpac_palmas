package br.com.itpacpalmas.api_sig_lab_itpac.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface PeriodoRepository extends JpaRepository<Periodo, Integer> {

	@Query("SELECT p from periodo p where p.ativo = true")
    List<Periodo> findByAtivo();
}