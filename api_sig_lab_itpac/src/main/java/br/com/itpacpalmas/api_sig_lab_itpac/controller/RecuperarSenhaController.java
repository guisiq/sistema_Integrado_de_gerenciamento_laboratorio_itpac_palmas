package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.RecuperarSenha;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.RecuperarSenhaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;
import jakarta.mail.Message;



public class RecuperarSenhaController {
	private String codigo;
	private String senha;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	RecuperarSenhaRepository recuperarSenhaRepository;
	
	public void alterarSenha() {
		
		
		
		RecuperarSenha obj = null;
		try {
			obj = recuperarSenhaRepository.findByCodigo(getCodigo());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(obj == null || obj.getDataLimite().isBefore(LocalDateTime.now()) || obj.getUtilizado() == true) {
			
	
		}
		else {
			Usuario usu = obj.getUsuario();
			usu.setSenha(getSenha());
			obj.setUtilizado(true);
			try {
				repoUsu.adicionar(usu);
				repo.adicionar(obj);
				Message.addInfoMessage("Senha alterada com sucesso.");
			} catch (RepositoryException e) {
				Message.addErrorMessage("Problemas ao alterar senha.");
				e.printStackTrace();
			}
			limpar();
		}
		
		
		
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
