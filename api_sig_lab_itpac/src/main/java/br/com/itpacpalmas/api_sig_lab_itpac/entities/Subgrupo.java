package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity(name="subgrupo")
public class Subgrupo {

	// ---------- Atributos
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsubgrupo")
	private Integer id;
	
	@Column(nullable = false, length = 60)
	private String nome;
	
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "professor")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "disciplina")
	private Disciplina disciplina;

	@ManyToMany
	@JoinTable(name="aluno_subgrupo",
	        joinColumns={@JoinColumn(name="aluno")},
	        inverseJoinColumns={@JoinColumn(name="subgrupo")})
	private List<Aluno> alunos;

	// ---------- Getters e Setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
/*
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
*/
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}