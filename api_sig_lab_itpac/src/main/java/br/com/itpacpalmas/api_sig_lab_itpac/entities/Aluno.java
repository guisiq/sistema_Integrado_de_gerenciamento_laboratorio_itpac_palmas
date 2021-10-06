package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity(name="aluno")
public class Aluno {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
	@OneToOne(cascade = { CascadeType.ALL})
    @JoinColumn(name = "pessoa")
	private Pessoa pessoa ;
	private String matricula ;
	private boolean Ativo ;
	
	public boolean isAtivo() {
		return Ativo;
	}
	public void setAtivo(boolean ativo) {
		Ativo = ativo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
