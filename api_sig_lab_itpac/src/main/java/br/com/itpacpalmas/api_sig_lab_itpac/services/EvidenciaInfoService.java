package br.com.itpacpalmas.api_sig_lab_itpac.services;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Agendamento;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aluno;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aula;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Disciplina;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.EvidenciaInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.*;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AgendamentoRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AulaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.DisciplinaRepository;


@Service
public class EvidenciaInfoService {
    @Autowired
    private AulaRepository repo;
    @Autowired
    AgendamentoRepository agendamentoRepository;

    public EvidenciaInfo find(Integer id){
        // EvidenciaInfo info ;
        Aula aula = repo.findById(id).get();
        return convertToInfo(aula);
    }
    public EvidenciaInfo convertToInfo(Aula aula) {
        EvidenciaInfo info = new EvidenciaInfo();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        info.setId(aula.getId());
        Agendamento agendamento = agendamentoRepository.findById(aula.getAgendamento().getId()).get();
        info.setData(agendamento.getData());
        info.setSala(agendamento.getSala().getNome());
        info.setSubgrupo(agendamento.getSubgrupo().getNome());
        info.setIdagendamento(aula.getAgendamento().getId());
        info.setNomeAtividade(aula.getNomeAtividade());
        info.setHorasAprendizagem(aula.getHorasAprendizagem());
        info.setCodigo(aula.getCodigo());
        info.setArquivosUrl("");
        info.setPresencaUrl("");
        return info;
    }
    public Aula convertToAula(EvidenciaInfo info) {
        Aula aula = new Aula();
        //DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        //info.setId(aula.getId());
        Agendamento agendamento = new Agendamento();
        agendamento.setId(info.getIdagendamento());
        aula.setAgendamento(agendamento);
        aula.setNomeAtividade(info.getNomeAtividade());
        aula.setHorasAprendizagem(info.getHorasAprendizagem());
        aula.setCodigo(info.getCodigo());
        //aula.setId(info.getId());
        return aula;
    }
    public List<EvidenciaInfo> ConvertList(List<Aula> aulas) {
        List<EvidenciaInfo> infos = new ArrayList<EvidenciaInfo>();
        aulas.forEach(aula ->{
            EvidenciaInfo info = convertToInfo(aula);
            infos.add(info);
        });
        return infos;
    }
    public List<EvidenciaInfo> findAll(){
        return ConvertList(repo.findAll());
    }
    public EvidenciaInfo creat(EvidenciaInfo evidencia) {

        Aula aula = convertToAula(evidencia);
        Aula aulaSalva = repo.save(aula);
        aulaSalva = repo.findById(aulaSalva.getId()).get();
        Agendamento agendamento = agendamentoRepository.findById(aula.getAgendamento().getId()).get();
        aulaSalva.setAlunosPresentes(agendamento.getSubgrupo().getAlunos());
        return convertToInfo(aulaSalva);
    }
    public EvidenciaInfo Delete(Integer id) {
        Aula aula = repo.findById(id).get();
        repo.delete(aula);
        return convertToInfo(aula);
    }
    public EvidenciaInfo Alter(EvidenciaInfo evidencia) {
        Aula aula = convertToAula(evidencia);
        aula.setId(evidencia.getId());
        Aula aulaSalva = repo.save(aula);
        return convertToInfo(aulaSalva);
    }
    public List<Aula> byPeriodo(int id) {
        return repo.aulasByPeriodo(id);
    }
    public Double horasbyPeriodo(int id) {
        List<Aula> aulasByPeriodo = repo.aulasByPeriodo(id);
        Double horas = 0.0 ;
        for (Aula a : aulasByPeriodo) {
            if(a.getHorasAprendizagem() != null){
                horas += a.getHorasAprendizagem();
            }
        }
        return horas;
    }
}

