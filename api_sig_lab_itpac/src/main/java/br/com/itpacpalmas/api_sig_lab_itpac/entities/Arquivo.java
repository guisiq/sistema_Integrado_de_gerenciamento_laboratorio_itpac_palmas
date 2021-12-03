package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity()

public class Arquivo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String Caminho;
    private String decricao;


    
    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    /**
     *
     * @return Integer
     * @author lopes lopes@email.com
     * @date 2021/11/23 10:40:23
     */
    public Integer getId() {
        return this.id;
    }

    /**
     *
     * @param id id
     * @return Integer
     * @author lopes lopes@email.com
     * @date 2021/11/23 10:40:23
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return String
     * @author lopes lopes@email.com
     * @date 2021/11/23 10:40:23
     */
    public String getCaminho() {
        return this.Caminho;
    }

    /**
     *
     * @param Caminho Caminho
     * @return String
     * @author lopes lopes@email.com
     * @date 2021/11/23 10:40:23
     */
    public void setCaminho(String Caminho) {
        this.Caminho = Caminho;
    }
}
