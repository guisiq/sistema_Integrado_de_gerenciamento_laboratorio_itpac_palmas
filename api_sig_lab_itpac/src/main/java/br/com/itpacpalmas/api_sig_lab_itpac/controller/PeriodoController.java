package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Periodo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PeriodoRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.*;

@RestController
@RequestMapping("/api/periodo")
public class PeriodoController {

	@Autowired
    PeriodoRepository periodoRepository;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Periodo salvarItem(@RequestBody Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    @GetMapping("/{id}")
    public Periodo get(@PathVariable(value = "id") int id) {
        return periodoRepository.findById(id).orElseThrow();
    }
    
    @GetMapping
    public List<Periodo> listaPeriodo() {
        return periodoRepository.findAll();
    }

    @PutMapping
    public Periodo atualizaPeriodo(@RequestBody Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    @PatchMapping("desativar/{id}")
    public void desativar(@PathVariable(value = "id") int id) {

        Periodo p = periodoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("periodo com id especificado nao foi encontrado "));
        p.setAtivo(false);
        periodoRepository.save(p);
    }
    @PatchMapping("ativar/{id}")
    public void ativar(@PathVariable(value = "id") int id) {

        Periodo p = periodoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("periodo com id especificado nao foi encontrado "));
        p.setAtivo(true);
        periodoRepository.save(p);
    }
    
}
