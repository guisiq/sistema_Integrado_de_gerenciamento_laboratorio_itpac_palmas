package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;


@Entity(name="professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "pessoa")
    private Pessoa pessoa;
    private boolean ativo;
    private String matricula;


public Integer getId() {
    return id;
}
public void setId(Integer id) {
    this.id = id;
}
public boolean isAtivo() {
    return ativo;
}
public void setAtivo(boolean ativo) {
    this.ativo = ativo;
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
