package br.com.itpacpalmas.api_sig_lab_itpac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.RecuperarSenha;

@Repository
public interface RecuperarSenhaRepository extends JpaRepository<RecuperarSenha, Integer>{

}
