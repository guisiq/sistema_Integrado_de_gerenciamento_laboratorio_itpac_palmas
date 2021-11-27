package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Arquivo;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Manual;
@Component
public class ArquivoResponseVO implements Serializable{
 
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileType;
	private long size;
	private String fileDownloadUri;
	private String Descricao;
	private int id;


	
	public ArquivoResponseVO() {
	}
	
	public ArquivoResponseVO(String fileName, String fileDownloadUri, String fileType, long size) {
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
	public static ArquivoResponseVO convert(Arquivo Arquivo,String fileDownloadUri) {
		ArquivoResponseVO retorno = new ArquivoResponseVO();
		retorno.fileName = Arquivo.getCaminho();
		retorno.fileDownloadUri = fileDownloadUri +Arquivo.getCaminho();
		retorno.id = Arquivo.getId();
		return retorno ;
	}

    public static List<ArquivoResponseVO> convertList(List<Arquivo> lArquivos,String fileDownloadUri) {
        List<ArquivoResponseVO> out = new ArrayList<ArquivoResponseVO>();
		lArquivos.forEach(
			manual -> out.add(
						convert(manual,fileDownloadUri)
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