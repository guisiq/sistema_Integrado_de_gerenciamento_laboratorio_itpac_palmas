package br.com.itpacpalmas.api_sig_lab_itpac.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import br.com.itpacpalmas.api_sig_lab_itpac.controller.ManualController;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ManualInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ManualRepository;

public class ManualServiss {
    @Autowired
    ManualRepository repo;
    public List<ManualInfo> buscarTodosInfo() {
        return ManualInfo.convertList(repo.findAll()); 
        
    }
    
    public ManualInfo buscarbyIdInfo(int Id) {
        //return ManualInfo.convert(repo.findById(Id).orElseThrow()); 
        return ManualInfo.convert(repo.findById(Id).orElse(null)); 
        
    }

    public MultipartFile buscarDocbyId() {
        return null;
    }
    
}
