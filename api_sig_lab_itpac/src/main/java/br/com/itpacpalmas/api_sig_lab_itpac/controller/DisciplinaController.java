package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Disciplina;
import br.com.itpacpalmas.api_sig_lab_itpac.services.DisciplinaService;

@RestController
@RequestMapping(value = "api/gerenciamento/disciplina")

public class DisciplinaController {
    @Autowired
    private DisciplinaService service;



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Disciplina> find(@PathVariable Integer id){
        Disciplina obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Disciplina obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(obj.getId()).toUri();

            System.out.println("Cadastro realizado");
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Disciplina obj, @PathVariable Integer id){
        //Garantir que realmente é o objeto do ID
        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<Disciplina>> findAll(){
        List<Disciplina> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PatchMapping(value="/desativar/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disciplina> disable (@PathVariable (value = "id")Integer id){
        service.disable(id);
        return ResponseEntity.noContent().build();
    }
}
