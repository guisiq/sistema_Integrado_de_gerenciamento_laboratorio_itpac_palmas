package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.com.itpacpalmas.api_sig_lab_itpac.exception.ResourceNotFoundException;
@CrossOrigin
@RestController
@RequestMapping("api/subgrupo")
public class SubgrupoController {

	
	@Autowired
	SubgrupoRepository subgrupoRepository;
	
	@PostMapping()
	public Subgrupo add(@RequestBody Subgrupo subgrupo) {
		subgrupo.setAtivo(true);
	    return subgrupoRepository.save(subgrupo);	
	}
	
	
	@GetMapping(value="/{id}")
	public Optional<Subgrupo> findById(@PathVariable(value="id") int id) {
	  return subgrupoRepository.findById(id);	
	}
	
	@GetMapping("getAll/{filtro}")
	public List<Subgrupo> getAll(@PathVariable (value = "filtro") boolean filtro){
		List<Subgrupo> retorno = subgrupoRepository.findAll();
		if (filtro) {
			retorno.removeIf(p -> !p.getAtivo()); 
		}
		return retorno;
	}
	
	@PutMapping()
	public Subgrupo alter(@RequestBody Subgrupo subgrupo) {
	  return subgrupoRepository.save(subgrupo);	
	}
	
	@PatchMapping(value="/desativar/{id}")
	public ResponseEntity<Subgrupo> disable(@PathVariable( value =  "id") Integer id) {
		    Subgrupo subgrupo = subgrupoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("subgrupo com id especificado nao foi encontrado "));
			subgrupo.setAtivo(false);
			subgrupoRepository.save(subgrupo);
			return ResponseEntity.status(HttpStatus.OK).body(subgrupo);
	}
	@PatchMapping(value="/Ativar/{id}")
	public ResponseEntity<Subgrupo> ativar(@PathVariable( value =  "id") Integer id) {
		    Subgrupo subgrupo = subgrupoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("subgrupo com id especificado nao foi encontrado "));
			subgrupo.setAtivo(true);
			subgrupoRepository.save(subgrupo);
			return ResponseEntity.status(HttpStatus.OK).body(subgrupo);
	}
	
}
