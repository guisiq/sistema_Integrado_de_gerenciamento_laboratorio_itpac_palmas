package br.com.itpacpalmas.api_sig_lab_itpac.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix= "file")
public class FileStorageConfig {

	private String uploadManualDir;
	private String uploadEvidenciaDir;

	/**
	 * 功能描述: uploadEvidenciaDir
	 *
	 * @return String
	 * @author lopes lopes@email.com
	 * @date 2021/11/23 11:28:21
	 */
	public String getUploadEvidenciaDir() {
		return this.uploadEvidenciaDir;
	}

	/**
	 *
	 * @param uploadEvidenciaDir uploadEvidenciaDir
	 * @return String
	 * @author lopes lopes@email.com
	 * @date 2021/11/23 11:28:21
	 */
	public void setUploadEvidenciaDir(String uploadEvidenciaDir) {
		this.uploadEvidenciaDir = uploadEvidenciaDir;
	}


	/**
	 *
	 * @return String
	 * @author lopes lopes@email.com
	 * @date 2021/11/23 11:30:10
	 */
	public String getUploadManualDir() {
		return this.uploadManualDir;
	}

	/**
	 *
	 * @param uploadManualDir uploadManualDir
	 * @return String
	 * @author lopes lopes@email.com
	 * @date 2021/11/23 11:30:10
	 */
	public void setUploadManualDir(String uploadManualDir) {
		this.uploadManualDir = uploadManualDir;
	}
	
}