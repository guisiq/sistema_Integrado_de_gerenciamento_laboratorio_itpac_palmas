package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aluno;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AlunoRepository;

@RestController
@RequestMapping("api/aluno")
@CrossOrigin
public class AlunoController {

	@Autowired
	AlunoRepository alunoRepository;
	
	
	@PostMapping
	public Aluno add(@RequestBody Aluno aluno) {
		 aluno.setAtivo(true);
	  return alunoRepository.save(aluno);	
	}
	
	@GetMapping(value="/{id}")
	public Optional<Aluno> findById(@PathVariable(value="id") int id) {
	  return alunoRepository.findById(id);	
	}
	
	@GetMapping
	public List<Aluno> findAll() {
	  return alunoRepository.findAll();	
	}
	
	@PutMapping
	public Aluno alter(@RequestBody Aluno aluno) {
	  return alunoRepository.save(aluno);	
	}
	
	@PatchMapping(value="/desativar/{id}")
	public ResponseEntity<Aluno> disable(@PathVariable( value =  "id") Integer id) {
		try{
            Aluno aluno = alunoRepository.findById(id).get();
			aluno.setAtivo(false);
			alunoRepository.save(aluno);
			return ResponseEntity.status(HttpStatus.OK).body(aluno);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	@PatchMapping(value="/Ativar/{id}")
	public ResponseEntity<Aluno> ativar(@PathVariable( value =  "id") Integer id) {
		try{
            Aluno aluno = alunoRepository.findById(id).get();
			aluno.setAtivo(true);
			alunoRepository.save(aluno);
			return ResponseEntity.status(HttpStatus.OK).body(aluno);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
		

		
	
}
