package br.com.itpacpalmas.api_sig_lab_itpac.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
}
