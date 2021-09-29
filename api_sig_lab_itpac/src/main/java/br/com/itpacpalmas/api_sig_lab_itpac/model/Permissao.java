package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Permissao {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermissao")
    private Long idPermissao;
    private String nome;

    
    public Permissao() {

    }
    
    public Permissao(Long idPermissao, String nome) {
        this.idPermissao = idPermissao;
        this.nome = nome;
    }
    public Long getIdPermissao() {
        return idPermissao;
    }
    public void setIdPermissao(Long idPermissao) {
        this.idPermissao = idPermissao;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
