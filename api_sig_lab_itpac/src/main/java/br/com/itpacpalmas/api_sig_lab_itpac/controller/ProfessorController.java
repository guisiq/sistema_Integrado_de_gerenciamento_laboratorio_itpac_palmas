package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Professor;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ProfessorRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.ResourceNotFoundException;


@RestController
@RequestMapping( value ="/api/professores")
@CrossOrigin
public class ProfessorController {

@Autowired
ProfessorRepository professorRepository;

@GetMapping("getAll/{filtro}")
public List<Professor> getAll(@PathVariable (value = "filtro") boolean filtro){
    List<Professor> retorno = professorRepository.findAll();
    if (filtro) {
        retorno.removeIf(p -> !p.isAtivo()); 
    }
    return retorno;
}
@GetMapping(value="/{id}")
public Optional<Professor> getId(@PathVariable (value = "id") int id){
    return professorRepository.findById(id);
}

@PostMapping()
public Professor add(@RequestBody Professor professor){
    return professorRepository.save(professor);
}
@PutMapping()
public Professor alter(@RequestBody Professor professor){
    return professorRepository.save(professor);
}

@PatchMapping(value="/desativar/{id}")
public ResponseEntity<Professor> disable(@PathVariable (value = "id" ) Integer id){

    Professor professor = professorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("professor com o id descrito nao foi encontrado "));
    professor.setAtivo(false);
    professorRepository.save(professor);
    return ResponseEntity.status(HttpStatus.OK).body(professor);
}
@PatchMapping(value="/ativar/{id}")
public ResponseEntity<Professor> ativar(@PathVariable (value = "id" ) Integer id){

    Professor professor = professorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("professor com o id descrito nao foi encontrado "));
    professor.setAtivo(true);
    professorRepository.save(professor);
    return ResponseEntity.status(HttpStatus.OK).body(professor);
}
    
}


