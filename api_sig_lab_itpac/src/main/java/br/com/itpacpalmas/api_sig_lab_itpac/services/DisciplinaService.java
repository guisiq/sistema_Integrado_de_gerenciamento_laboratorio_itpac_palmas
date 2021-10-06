package br.com.itpacpalmas.api_sig_lab_itpac.services;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Disciplina;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.DisciplinaRepository;


@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository repo;

    public Disciplina find(Integer id){
        Optional<Disciplina> obj = repo.findById(id);
        return obj.orElse(null);
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
}

