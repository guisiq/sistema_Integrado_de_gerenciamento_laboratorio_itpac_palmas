package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Status {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idstatus;
	private Long id;

    private String descricao;
     

    public Long getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(Long idstatus) {
        this.idstatus = idstatus;
    }

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
