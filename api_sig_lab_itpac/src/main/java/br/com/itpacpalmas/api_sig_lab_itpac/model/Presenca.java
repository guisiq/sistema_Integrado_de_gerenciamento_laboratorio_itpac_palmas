package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="presenca")
public class Presenca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idpresenca;
	private boolean presente;
	@OneToOne
	@JoinColumn(name ="aula")
	private Aula aula;
	@JoinColumn(name ="aluno")
	private Aluno aluno;
	
	public int getIdpresenca() {
		return idpresenca;
	}
	public void setIdpresenca(int idpresenca) {
		this.idpresenca = idpresenca;
	}
	public boolean isPresente() {
		return presente;
	}
	public void setPresente(boolean presente) {
		this.presente = presente;
	}

	
	
	
}
