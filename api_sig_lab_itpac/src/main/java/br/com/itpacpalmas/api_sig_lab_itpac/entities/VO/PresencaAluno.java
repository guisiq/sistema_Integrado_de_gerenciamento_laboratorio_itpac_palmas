package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO;

public class PresencaAluno {

    private Integer id;
    private String nome;
    private boolean presente;



    public String getNome() {
        return this.nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isPresente() {
        return this.presente;
    }

    public boolean getPresente() {
        return this.presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    
}
