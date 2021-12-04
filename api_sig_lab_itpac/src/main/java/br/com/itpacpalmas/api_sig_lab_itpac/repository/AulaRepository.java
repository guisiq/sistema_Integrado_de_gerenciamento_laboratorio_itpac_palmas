package br.com.itpacpalmas.api_sig_lab_itpac.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
    @Query(value = "SELECT t.* FROM public.aula t where  exists((select idagendamento from  agendamento, diciplina where agendamento.disciplina = diciplina.iddiciplina AND   diciplina.periodo = :id AND   agendamento.idagendamento = t.agendamento))",nativeQuery=true)
    public List<Aula> aulasByPeriodo(Integer id);
}
