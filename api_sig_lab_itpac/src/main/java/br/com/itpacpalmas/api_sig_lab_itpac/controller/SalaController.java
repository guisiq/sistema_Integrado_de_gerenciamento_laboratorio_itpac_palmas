package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Sala;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.SalaRepository;

@RestController
@RequestMapping("/api/sala")
@CrossOrigin
public class SalaController {

    @Autowired
    SalaRepository salaRepository;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Sala salvarItem(@RequestBody  Sala sala) {
        return salaRepository.save(sala);
    }

    @GetMapping("/{id}")
    public Sala listSala(@PathVariable(value = "id") int id) {
        return salaRepository.findById(id);

    }
    @GetMapping
    public List<Sala> listaSala() {
        return salaRepository.findAll();
    }


    @PutMapping
    public Sala atualizaSala(@RequestBody Sala sala) {
        return salaRepository.save(sala);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable int id) {
        salaRepository.deleteById(id);
    }
    
}
