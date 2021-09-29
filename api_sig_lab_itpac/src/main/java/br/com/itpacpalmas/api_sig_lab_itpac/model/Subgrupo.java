//package br.com.itpacpalmas.api_sig_lab_itpac.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.JoinColumn;
//
//@Entity
//public class Subgrupo {
//
//	// ---------- Atributos
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	
//	@Column(nullable = false, length = 60)
//	private String nome;
//	
//	@OneToOne
//	@JoinColumn(name = "professor_id", referencedColumnName = "id")
//	private Professor professor;
//	
//	@OneToOne
//	@JoinColumn(name = "disciplina_id", referencedColumnName = "id")
//	private Disciplina disciplina;
//
//	// ---------- Getters e Setters
//	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//	public Professor getProfessor() {
//		return professor;
//	}
//
//	public void setProfessor(Professor professor) {
//		this.professor = professor;
//	}
//
//	public Disciplina getDisciplina() {
//		return disciplina;
//	}
//
//	public void setDisciplina(Disciplina disciplina) {
//		this.disciplina = disciplina;
//	}
//	
//}