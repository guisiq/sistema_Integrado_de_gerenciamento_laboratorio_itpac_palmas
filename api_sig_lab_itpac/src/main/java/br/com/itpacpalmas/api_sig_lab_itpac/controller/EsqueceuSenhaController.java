package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.Services.EmailService;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.RecuperarSenha;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.RecuperarSenhaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;

@RestController
@RequestMapping(value="api/forgotpass")
public class EsqueceuSenhaController {
	
@Autowired
UsuarioRepository usuarioRepository;
@Autowired
RecuperarSenhaRepository recuperarSenhaRepository;

	@GetMapping
	public boolean enviarEmail(@RequestParam(name = "email") String email) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findByEmail(email);
		} catch (Exception e) {
			return false;
		}
		// gerando codigo aleatorio
		Random r = new Random();
		DecimalFormat format = new DecimalFormat("Afya-000000");
		String codigo = format.format(r.nextInt(1000000));
		
		RecuperarSenha entity = new RecuperarSenha();
		entity.setCodigo(codigo);
		entity.setUsuario(usuario);
		entity.setUtilizado(false);
		// definindo 24 horas de tempo limite
		entity.setDataLimite(LocalDateTime.now().plusDays(1));
		
		try {
			recuperarSenhaRepository.save(entity);
			EmailService mail = new EmailService(usuario.getPessoa().getEmail(), 
									"Esqueceu a Senha",
									codigo+" é seu código de recuperação de senha.");
			mail.enviar();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@GetMapping(value="/alterpass")
	public Usuario alterarSenha(@RequestParam String codigo,@RequestParam String senha) {
	
		RecuperarSenha obj = recuperarSenhaRepository.findByCodigo(codigo);
		if(obj == null || obj.getDataLimite().isBefore(LocalDateTime.now()) || obj.getUtilizado() == true) {
			
			return null;
		}
		else {
			Usuario usu = obj.getUsuario();
			System.out.println(usu.getPassword());
			usu.setPassword(senha);
			obj.setUtilizado(true);
			Usuario teste =usuarioRepository.save(usu);
			System.out.println(teste.getPassword());
			recuperarSenhaRepository.save(obj);
			
				
			return teste;
		}
		
		
		
	}



}
