package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Aluno {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
	private Pesoa pesoa ;
	private String matricula ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Pesoa getPesoa() {
		return pesoa;
	}
	public void setPesoa(Pesoa pesoa) {
		this.pesoa = pesoa;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
