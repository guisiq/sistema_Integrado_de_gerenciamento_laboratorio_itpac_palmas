package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	public Usuario add(@RequestBody Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);
		usuario.setAtivo(true);
	    return usuarioRepository.save(usuario);	
	}
	
	@GetMapping(value="/{id}")
	public Optional<Usuario> findById(@PathVariable(value="id") int id) {
	  return usuarioRepository.findById(id);	
	}
	
	@GetMapping
	public List<Usuario> findAll() {
	  return usuarioRepository.findAll();	
	}
	
	@PutMapping
	public Usuario alter(@RequestBody Usuario usuario) {
	  return usuarioRepository.save(usuario);	
	}
	
	@PatchMapping(value="/desativar/{id}")
	public ResponseEntity<Usuario> disable(@PathVariable( value =  "id") Integer id) {
		try{
            Usuario usuario = usuarioRepository.findById(id).get();
			usuario.setAtivo(false);
			usuarioRepository.save(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	@PatchMapping(value="/Ativar/{id}")
	public ResponseEntity<Usuario> ativar(@PathVariable( value =  "id") Integer id) {
		try{
			Usuario  usuario = usuarioRepository.findById(id).get();
			usuario.setAtivo(true);
			usuarioRepository.save(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
		

		
	
}
