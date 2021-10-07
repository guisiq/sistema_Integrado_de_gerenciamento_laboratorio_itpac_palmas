package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aluno;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Subgrupo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.SubgrupoRepository;

@RestController
@RequestMapping("api/subgrupo")
public class SubgrupoController {

	
	@Autowired
	SubgrupoRepository subgrupoRepository;
	
	@PostMapping(value="/add",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public Subgrupo add(@RequestBody Subgrupo subgrupo) {
		subgrupo.setAtivo(true);
	  return subgrupoRepository.save(subgrupo);	
	}
	
	
	@GetMapping(value="/get/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Optional<Subgrupo> findById(@PathVariable(value="id") int id) {
	  return subgrupoRepository.findById(id);	
	}
	
	@GetMapping(value="/getAll",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Subgrupo> findAll() {
	  return subgrupoRepository.findAll();	
	}
	
	@PutMapping(value="/alter",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Subgrupo alter(@RequestBody Subgrupo subgrupo) {
	  return subgrupoRepository.save(subgrupo);	
	}
	
	@PatchMapping(value="/desativar/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Subgrupo> disable(@PathVariable( value =  "id") Integer id) {
		try{
            Subgrupo subgrupo = subgrupoRepository.findById(id).get();
			subgrupo.setAtivo(false);
			subgrupoRepository.save(subgrupo);
			return ResponseEntity.status(HttpStatus.OK).body(subgrupo);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
		}
	
}
