package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


@RestController
@RequestMapping( value ="/api/professores")
public class ProfessorController {

@Autowired
ProfessorRepository professorRepository;

@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
public List<Professor> getAll(){
    return professorRepository.findAll();
}
@GetMapping(value="/listaProfessores/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
public Optional<Professor> getId(@PathVariable (value = "id") int id){
    return professorRepository.findById(id);
}

@PostMapping(value="/add",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public Professor add(@RequestBody Professor professor){
    professor.setAtivo(true);
    return professorRepository.save(professor);
}
@PutMapping(value="/alter",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
public Professor alter(@RequestBody Professor professor){

    return professorRepository.save(professor);
}

@PatchMapping(value="/disable/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Professor> disable(@PathVariable (value = "id" ) Integer id){
    try {
        Professor professor = professorRepository.findById(id).get();
        professor.setAtivo(false);
        professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }catch(Exception e){
        e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

}
    
}


