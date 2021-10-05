package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.itpacpalmas.api_sig_lab_itpac.Services.ManualServiss;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.PerfilUser;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ManualInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PerfilUserRepository;

// https://www.youtube.com/watch?v=DtC_KfU6b1o
@RestController
@RequestMapping("api/manual")
public class ManualController {
    @Autowired
    ManualServiss servises;
    // GET      ⇒/api/{V}/manual/info
    @GetMapping("/info")
    public  List<ManualInfo> getInfo() {
        return servises.buscarTodosInfo();
    }
    // GET      ⇒\api\{V}\manual\info\{ID}
    @GetMapping("/info/{id}")
    public ManualInfo getInfoById(@PathVariable int id) {
        return servises.buscarbyIdInfo(id );
    }
  
    // GET      ⇒\api\{V}\manual\doc\{ID}
    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getDocById(@PathVariable int id) {
      return null;
    }
  
    // POST    ⇒\api\{V}\manual\
    @PostMapping
    public void creat() {
        
    }
    // PUT      ⇒\api\{V}\manual\{ID}
    @PutMapping
    public void update() {
        
    }
// delete  ⇒\api\{V}\manual\{ID}
    @DeleteMapping
    public void delet() {
        
    }
}
