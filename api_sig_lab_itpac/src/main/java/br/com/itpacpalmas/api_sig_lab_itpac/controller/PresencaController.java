// package br.com.itpacpalmas.api_sig_lab_itpac.controller;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import br.com.itpacpalmas.api_sig_lab_itpac.entities.Presenca;
// import br.com.itpacpalmas.api_sig_lab_itpac.repository.PresencaRepository;


// @RestController
// @RequestMapping("/presenca")
// public class PresencaController {
	 
// 	@Autowired
// 	PresencaRepository PresencaRepository;
	
// 	@PostMapping(value="/add",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
// 	public Presenca add(@RequestBody Presenca presenca) {
// 		return PresencaRepository.save(presenca);	
// 	}
	
// 	@GetMapping(value="/get/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
// 	public Optional<Presenca> findById(@PathVariable(value="id") int id) {
// 		return PresencaRepository.findById(id);	
// 	}
	
	
// 	@GetMapping(value="/getAll",produces=MediaType.APPLICATION_JSON_VALUE)
// 	public List<Presenca> findAll() {
// 		return PresencaRepository.findAll();	
// 	}
	
// 	@PutMapping(value="/alter",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
// 	public void alter(@RequestBody Presenca perfil) {
// 		PresencaRepository.save(perfil);
		
// 	}
	
	
// }
