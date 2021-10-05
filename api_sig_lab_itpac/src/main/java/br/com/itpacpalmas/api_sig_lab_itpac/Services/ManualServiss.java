package br.com.itpacpalmas.api_sig_lab_itpac.Services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ManualInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ManualRepository;
@Service
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
    
/*
    public ResponseEntity<?> buscarDocbyId(int id) {
         // Sanitizar... (biblioteca, nomeDoArquivo.startsWith, regex)
       var manual = repo.findById(id).orElse(null);
       var filename = String.format("/home/geovaniggr/home/study/%s.pdf", manual.getDocumento());

       try {
           var file = new File(filename);
           var path = Paths.get(file.getAbsolutePath());
           var resource = new ByteArrayResource(Files.readAllBytes(path));
           return ResponseEntity
                   .ok()
                   .contentType(MediaType.APPLICATION_PDF)
                   .contentLength(file.length())
                   .body(resource);
       } catch (IOException e) {
           e.printStackTrace();
           return ResponseEntity.notFound().build();
       }
    }
*/    
}
