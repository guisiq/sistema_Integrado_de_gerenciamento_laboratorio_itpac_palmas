package br.com.itpacpalmas.api_sig_lab_itpac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, name = "senha")
    private String senha;
    @Column(nullable = false, name = "login")
    private String login;
    private PerfilUser perfil_user;
    private Pessoa pessoa;

    public Integer getId() {
        return id;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public PerfilUser getPerfil_user() {
        return perfil_user;
    }
    public void setPerfil_user(PerfilUser perfil_user) {
        this.perfil_user = perfil_user;
    }
    public void setIdUsuario(Integer id) {
        this.id = id;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

}
