package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;

@RestController
@RequestMapping("api/login")
public class LoginController {
    @Autowired
    private UsuarioRepository repo;
    @GetMapping(value = "/getroles")
    public List<Usuario> getroles() {
        return repo.findAll();
    }
    // @GetMapping(value = "getpermision")
    // public List<String> getroles() {

        
    // }
}
