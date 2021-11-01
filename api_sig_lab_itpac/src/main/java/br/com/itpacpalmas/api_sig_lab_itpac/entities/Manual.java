package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="manual")
public class Manual {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "idmanual")
    private Integer id;
    private String descricao;
    private String documento;
    @Column(columnDefinition = "boolean default false")
    private boolean ativo;


//-------------Getter e Setter
    
    public Integer getId() {
        return this.id;
    }

    public Manual() {
    }

    public Manual(Integer id, String descricao, String documento, boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.documento = documento;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

}
