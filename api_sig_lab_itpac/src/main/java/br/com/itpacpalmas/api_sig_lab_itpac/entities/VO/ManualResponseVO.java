package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Manual;
@Component
public class ManualResponseVO implements Serializable{
 
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileType;
	private long size;
	private String fileDownloadUri;
	private String Descricao;
	private int id;


	
	public ManualResponseVO() {
	}
	
	public ManualResponseVO(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}
	
	//#region geters e seters

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	//#endregion
	public static ManualResponseVO convert(Manual manual) {
		ManualResponseVO retorno = new ManualResponseVO();
		retorno.fileName = manual.getDocumento();
		retorno.fileDownloadUri = "/api/file/downloadFile/"+manual.getDocumento();
		retorno.Descricao = manual.getDescricao();
		retorno.id = manual.getId();
		return retorno ;
	}

    public static List<ManualResponseVO> convertList(List<Manual> lManuals) {
        List<ManualResponseVO> out = new ArrayList<ManualResponseVO>();
		lManuals.forEach(
			manual -> out.add(
						convert(manual)
						)
		);
		return out;
    }

    public Manual toManual() {
        Manual retorno = new Manual();
		retorno.setDescricao(this.Descricao);
		retorno.setDocumento(this.fileName);
		return retorno;
    }
}