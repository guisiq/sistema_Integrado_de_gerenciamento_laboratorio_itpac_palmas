package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Permissao")
public class Permissao {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermissao")
    private Long id;
    private String nome;

    
    public Permissao() {

    }
    
    public Permissao(Long idPermissao, String nome) {
        this.id = idPermissao;
        this.nome = nome;
    }
    public Long getIdPermissao() {
        return id;
    }
    public void setIdPermissao(Long idPermissao) {
        this.id = idPermissao;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
