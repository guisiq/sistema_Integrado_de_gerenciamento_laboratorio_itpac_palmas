package br.com.itpacpalmas.api_sig_lab_itpac.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Periodo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PeriodoRepository;


@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository repo;
    
    public ResponseEntity<Periodo> disable(Integer id){
        try{
            Periodo periodo = repo.findById(id).get();
			periodo.setAtivo(false);
			repo.save(periodo);
			return ResponseEntity.status(HttpStatus.OK).body(periodo);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
    }
}