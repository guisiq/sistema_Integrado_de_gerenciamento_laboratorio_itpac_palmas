package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="status")
public class Status {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idstatus")
    private Long id;

    private String descricao;

    public Status(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
     
    public Status() {

    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    


}
