package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="usuario")
public class Usuario{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column( name = "idusuario")
    private Integer id;
    @Column( name = "senha")
    private String senha;
    @Column( name = "login")
    private String login;
    @ManyToOne
    @JoinColumn(name ="perfil_user")
    private PerfilUser perfilUser;
    @OneToOne
    @JoinColumn(name ="pessoa")
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
        return perfilUser;
    }
    public void setPerfil_user(PerfilUser perfil_user) {
        this.perfilUser = perfil_user;
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
