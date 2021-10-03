package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Permissao;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PermissaoRepository;

@RestController
@RequestMapping(value = "/api/permissao")
public class PermissaoController {
    @Autowired
    PermissaoRepository permissaoRepository ;

    @PostMapping("/cadastrar")
    public ResponseEntity<Permissao> cadastrar(@RequestBody Permissao permissao) {
        Permissao permissaoSalvo;
        
        try {
            permissaoSalvo = permissaoRepository.save(permissao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalvo);
    }

   

    @GetMapping("/buscarTodos")
    public List<Permissao> buscarTodos() {
        return permissaoRepository.buscarTodos();
    }

}
