package br.com.itpacpalmas.api_sig_lab_itpac.repository;



import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query(value = "select * from agendamento a  where a.ativo = true", nativeQuery = true)
    List<Agendamento> buscarTodos();

    //@Query(value = "select * from agendamento a  where a.data = :data" , nativeQuery = true)
    List<Agendamento> findByData(LocalDate data);

    @Query(value = "SELECT * FROM agendamento a WHERE a.data BETWEEN :inicio and :termino", nativeQuery = true)
    List<Agendamento> findByPeriod(LocalDate inicio, LocalDate termino);

    Agendamento findById(int id);
}
