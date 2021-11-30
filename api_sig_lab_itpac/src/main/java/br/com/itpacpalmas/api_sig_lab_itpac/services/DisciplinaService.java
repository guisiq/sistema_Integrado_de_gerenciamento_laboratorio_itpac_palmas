package br.com.itpacpalmas.api_sig_lab_itpac.services;


import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Disciplina;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.*;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.DisciplinaRepository;


@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository repo;

    public Disciplina find(Integer id){
        Optional<Disciplina> obj = repo.findById(id);
        return obj.orElseThrow( () -> new ResourceNotFoundException("entidade nao encontrada no banco "));
    }

    public Disciplina insert (Disciplina obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Disciplina update (Disciplina obj){
        find(obj.getId());
        return repo.save(obj);
    }

    public List<Disciplina> findAll(){
        return repo.findAll();
    }
    
    public ResponseEntity<Disciplina> disable(Integer id){
        try{
            Disciplina disciplina = repo.findById(id).get();
			disciplina.setAtivo(false);
			repo.save(disciplina);
			return ResponseEntity.status(HttpStatus.OK).body(disciplina);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
    }
    public ResponseEntity<Disciplina> ativar(Integer id){
        try{
            Disciplina disciplina = repo.findById(id).get();
			disciplina.setAtivo(true);
			repo.save(disciplina);
			return ResponseEntity.status(HttpStatus.OK).body(disciplina);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
    }
}

