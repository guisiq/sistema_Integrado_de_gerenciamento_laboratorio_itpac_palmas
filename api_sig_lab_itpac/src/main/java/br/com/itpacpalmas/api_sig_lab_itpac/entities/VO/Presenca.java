package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO; 

import java.util.List;


public class Presenca {

    private Integer id;
    private List <PresencaAluno> listaAluno;
    
    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PresencaAluno> getListaAluno() {

        return this.listaAluno;
    }

    public void setListaAluno(List<PresencaAluno> listaAluno) {
        this.listaAluno = listaAluno;
    }

    
}
