package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.PerfilUser;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PerfilUserRepository;

@RestController
@RequestMapping("/perfil-user")
public class PerfilUserController {
	@Autowired
	PerfilUserRepository PerfilUserRepository;
	
	
	
	@PostMapping(value="/add",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public PerfilUser add(@RequestBody PerfilUser perfil) {
		return PerfilUserRepository.save(perfil);	
	}
	
	@GetMapping(value="/get/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Optional<PerfilUser> findOne(@PathVariable(value="id") Integer id) {
		return PerfilUserRepository.findById(id);	
	}
	
	@GetMapping(value="/getAll",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<PerfilUser> findAll() {
		return PerfilUserRepository.findAll();	
	}
	
	@PutMapping(value="/alter",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public void alter(@RequestBody PerfilUser perfil) {
		PerfilUserRepository.save(perfil);
		
	}
}
