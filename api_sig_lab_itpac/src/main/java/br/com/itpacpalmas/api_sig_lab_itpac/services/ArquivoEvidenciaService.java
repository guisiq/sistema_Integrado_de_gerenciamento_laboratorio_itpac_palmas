package br.com.itpacpalmas.api_sig_lab_itpac.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.itpacpalmas.api_sig_lab_itpac.config.FileStorageConfig;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Arquivo;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aula;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Manual;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ArquivoResponseVO;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.FileStorageException;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.MyFileNotFoundException;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.ResourceNotFoundException;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ArquivoRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AulaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ManualRepository;

@Service
public class ArquivoEvidenciaService {

	private final Path fileStorageLocation;
	@Autowired
    ArquivoRepository ArquivoRepo;
	@Autowired
    AulaRepository aulaRepo;
	
	@Autowired
	public ArquivoEvidenciaService(FileStorageConfig fileStorageConfig) {
		
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadEvidenciaDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored", e);
		}
	}
	
	public String storeFile(MultipartFile file, Integer id,Integer indice ) {
		// String extencao = file.getOriginalFilename();
		// extencao = extencao.split(".")[1];
		// String fileName = StringUtils.cleanPath("manual"+id+extencao);
		String fileName = StringUtils.cleanPath("evidencia"+id+"_"+indice+"_"+file.getOriginalFilename());
		
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalida path sequence " + fileName);
			}
			
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
		
	}

	
    public List<ArquivoResponseVO> buscarTodosInfo() {
		return ArquivoResponseVO.convertList(ArquivoRepo.findAll(),fileStorageLocation.toString()); 
    }
    
    public ArquivoResponseVO buscarbyIdInfo(int Id) {
        //return ArquivoResponseVO.convert(repo.findById(Id).orElseThrow()); 
        return ArquivoResponseVO.convert(ArquivoRepo.findById(Id).orElse(null),fileStorageLocation.toString()); 
        
    }


	public ArquivoResponseVO uploadFile(MultipartFile file,int id) {
		Arquivo getArquivo = ArquivoRepo.save(new Arquivo());
		String fileName = this.storeFile(file,getArquivo.getId(),id);
		getArquivo.setCaminho(fileName);;
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/downloadFile/")
				.path(fileName)
				.toUriString();
		
		ArquivoResponseVO retorno = new ArquivoResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		retorno.setId(getArquivo.getId());
		getArquivo = ArquivoRepo.save(getArquivo);
		Aula aula = aulaRepo.findById(id).get();
		aula.getArquivos().add(getArquivo);
		aulaRepo.save(aula);
		return retorno;
	
	}

	
	public ResponseEntity<Resource> downloadFile(int id, HttpServletRequest request) {
		String fileName = ArquivoRepo.findById(id).get().getCaminho();
		Resource resource = this.loadFileAsResource(fileName);
		
		String contentType = null;
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			//logger.info("Could not determine file type!");
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
				
	}

    public ArquivoResponseVO update(int id ,String descricao ) {
		Arquivo retorno = ArquivoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("entidade nao encontrada no banco "));
		ArquivoRepo.save(retorno);
		return ArquivoResponseVO.convert(retorno,fileStorageLocation.toString());
    }

	public void delete(int id) {
		ArquivoRepo.deleteById(id);
	}

	public List<ArquivoResponseVO> getByEvidencia(int id) {



		return ArquivoResponseVO.convertList(aulaRepo.findById(id).get().getArquivos(),fileStorageLocation.toString());
	}

    public List<ArquivoResponseVO> getById() {
        return null;
    }
}
