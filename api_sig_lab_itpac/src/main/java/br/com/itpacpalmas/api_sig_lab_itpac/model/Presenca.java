package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Presenca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idpresenca;
	private boolean presente;
//	private Aula aula;
//	private Aluno aluno;
	
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
//	public Aula getAula() {
//		return aula;
//	}
//	public void setAula(Aula aula) {
//		this.aula = aula;
//	}
//	public Aluno getAluno() {
//		return aluno;
//	}
//	public void setAluno(Aluno aluno) {
//		this.aluno = aluno;
//	}
	
	
	
}
