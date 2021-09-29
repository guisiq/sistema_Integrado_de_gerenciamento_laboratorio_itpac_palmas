//package br.com.itpacpalmas.api_sig_lab_itpac.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Aula {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	@Column(nullable = false, name = "codigo")
//	private String codigo;
//	@Column(nullable = false, name = "nome_atividade")
//	private String nome_atividade;
//	@Column(nullable = false, name = "horas_aprendizagem")
//	private Double horas_aprendizagem;
//	private Agendamento agendamento;
//
//	public Agendamento getAgendamento() {
//		return agendamento;
//	}
//
//	public void setAgendamento(Agendamento agendamento) {
//		this.agendamento = agendamento;
//	}
//
//	public Double getHoras_aprendizagem() {
//		return horas_aprendizagem;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setIdAula(Integer id) {
//		this.id = id;
//	}
//
//	public String getCodigo() {
//		return codigo;
//	}
//
//	public void setCodigo(String codigo) {
//		this.codigo = codigo;
//	}
//
//	public String getNome_atividade() {
//		return nome_atividade;
//	}
//
//	public void setNome_atividade(String nome_atividade) {
//		this.nome_atividade = nome_atividade;
//	}
//
//	public void setHoras_aprendizagem(Double horas_aprendizagem) {
//		this.horas_aprendizagem = horas_aprendizagem;
//	}
//}
