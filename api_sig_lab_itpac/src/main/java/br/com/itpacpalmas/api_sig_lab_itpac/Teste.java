package br.com.itpacpalmas.api_sig_lab_itpac;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class Teste {

	@GetMapping("/hello")
	public String teste() {
		return "Hello,word";
	}
	
	
	
}
