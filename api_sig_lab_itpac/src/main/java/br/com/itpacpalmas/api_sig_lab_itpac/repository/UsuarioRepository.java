package br.com.itpacpalmas.api_sig_lab_itpac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // @NatiQuery("SELECT u FROM Usuario u WHERE u.userName =:userName")
    Usuario findByUserName(String userName);

}
