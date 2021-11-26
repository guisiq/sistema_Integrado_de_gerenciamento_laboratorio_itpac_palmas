package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ArquivoResponseVO;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ArquivoResponseVO;
import br.com.itpacpalmas.api_sig_lab_itpac.services.ArquivoEvidenciaService;
import br.com.itpacpalmas.api_sig_lab_itpac.services.ManualService;


// https://www.youtube.com/watch?v=DtC_KfU6b1o
@RestController
@RequestMapping("api/evidencia/arquivo")
public class ArquivoEvidenciaController {
    @Autowired
	private ArquivoEvidenciaService servises;
    
    @GetMapping()
    public  ResponseEntity<List<ArquivoResponseVO>> getInfo(@RequestParam("idEvidencia")int idEvidencia,@RequestParam("idArquivo")int idArquivo) {   
        System.out.println();
        return ResponseEntity.ok().body(servises.buscarTodosInfo());
    }
    
    
    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getDocById(@PathVariable int id, HttpServletRequest request) {
        return servises.downloadFile(id, request);
    }
    
    @PostMapping("/{idEvidencia}")
	public ArquivoResponseVO uploadFile(@RequestParam("file") MultipartFile file,@PathVariable int idEvidencia) {
        return servises.uploadFile(file,idEvidencia);
    }
    
    @PatchMapping("/{id}/{descricao}")
    public void update(@PathVariable int id,@PathVariable String descricao) {
        servises.update(id, descricao);
    }

    @DeleteMapping("/{id}")
    public void delet(@PathVariable int id) {
       servises.delete(id); 
    }

}
