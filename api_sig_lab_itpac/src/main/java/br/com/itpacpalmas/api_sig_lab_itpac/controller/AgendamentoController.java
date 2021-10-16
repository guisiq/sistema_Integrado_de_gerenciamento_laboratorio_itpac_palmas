package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Agendamento;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AgendamentoRepository;

@RestController
@RequestMapping(value = "/api/periodo/Agendamentos")

public class AgendamentoController {
    @Autowired
    AgendamentoRepository agendamentoRepository;

    @PostMapping
    public ResponseEntity<Agendamento> cadastrar(@RequestBody Agendamento agendamento) {
        Agendamento agendamentoSalvo;

        try {
            agendamentoSalvo = agendamentoRepository.save(agendamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @PutMapping
    public ResponseEntity<Agendamento> atualizar(@RequestBody Agendamento Agendamento) {
        Agendamento AgendamentoSalvo;
        try {

            AgendamentoSalvo = agendamentoRepository.save(Agendamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoSalvo);
    }

    @GetMapping
    public List<Agendamento> buscarTodos() {
        return agendamentoRepository.buscarTodos();
    }

    @GetMapping("/{id}")
    public Agendamento listAgendamentoId(@PathVariable(value = "id") int id) {
        return agendamentoRepository.findById(id);

    }

    @PatchMapping(value = "/desativar/{id}")
    public ResponseEntity<Agendamento> disable(@PathVariable(value = "id") Integer id) {
        try {
            Agendamento agendamento = agendamentoRepository.findById(id).get();
            agendamento.setAtivo(false);
            agendamentoRepository.save(agendamento);
            return ResponseEntity.status(HttpStatus.OK).body(agendamento);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("buscarPorDatas/{data}")
    public List<Agendamento> findByData(@PathVariable(value = "data") String dataRecebida) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date dataFormatada = formato.parse(dataRecebida);

        return agendamentoRepository.findByData(dataFormatada);
    }

}
