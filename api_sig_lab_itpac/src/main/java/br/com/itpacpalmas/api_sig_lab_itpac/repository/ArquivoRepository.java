package br.com.itpacpalmas.api_sig_lab_itpac.repository;



import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    @Transactional
    @Modifying
    @Query( value ="delete from arquivos_aula where arquivo = :id",nativeQuery=true)
    public void deletAulaArquivo(int id);
}
