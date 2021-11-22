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
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Manual;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.ManualResponseVO;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.FileStorageException;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.MyFileNotFoundException;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.ResourceNotFoundException;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.ManualRepository;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	@Autowired
    ManualRepository repo;
	
	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored", e);
		}
	}
	
	public String storeFile(MultipartFile file, Integer id) {
		// String extencao = file.getOriginalFilename();
		// extencao = extencao.split(".")[1];
		// String fileName = StringUtils.cleanPath("manual"+id+extencao);
		String fileName = StringUtils.cleanPath("manual"+id+"_"+file.getOriginalFilename());
		
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

	
    public List<ManualResponseVO> buscarTodosInfo() {
		return ManualResponseVO.convertList(repo.findAll()); 
    }
    
    public ManualResponseVO buscarbyIdInfo(int Id) {
        //return ManualResponseVO.convert(repo.findById(Id).orElseThrow()); 
        return ManualResponseVO.convert(repo.findById(Id).orElse(null)); 
        
    }


	public ManualResponseVO uploadFile(MultipartFile file, String descricao) {
		Manual getManual = repo.save(new Manual());
		String fileName = this.storeFile(file,getManual.getId());
		getManual.setDescricao(descricao);
		getManual.setDocumento(fileName);;
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/Manual/doc/"+getManual.getId())
				//.path(fileName)
				.toUriString();
		
		ManualResponseVO retorno = new ManualResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		retorno.setDescricao(descricao);
		retorno.setId(getManual.getId());
		repo.save(getManual);
		return retorno;
	
	}

	
	public ResponseEntity<Resource> downloadFile(int id, HttpServletRequest request) {
		String fileName = repo.findById(id).get().getDocumento();
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

    public ManualResponseVO update(int id ,String descricao ) {
		Manual retorno = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("entidade nao encontrada no banco "));
		retorno.setDescricao(descricao);
		repo.save(retorno);
		return ManualResponseVO.convert(retorno);
    }

	public void delete(int id) {
		repo.deleteById(id);
	}

    public ManualResponseVO desativar(int id) {
		Manual manual = repo.findById(id).get();
		manual.setAtivo(false);
		return ManualResponseVO.convert(repo.save(manual));
    }

    public ManualResponseVO ativar(int id) {
		Manual manual = repo.findById(id).get();
		manual.setAtivo(true);
		return ManualResponseVO.convert(repo.save(manual));
    }

}
