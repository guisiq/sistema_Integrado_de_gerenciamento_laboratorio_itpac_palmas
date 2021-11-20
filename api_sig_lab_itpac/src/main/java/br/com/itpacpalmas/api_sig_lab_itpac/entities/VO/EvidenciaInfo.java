package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO;

public class EvidenciaInfo {
    
    private long id;
    private String sala;
    private String data;
    private String subgrupo;
    private String presencaUrl;
    private String arquivosUrl;
    
    //#region ------ Getter e Setter

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala = sala;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getSubgrupo() {
        return subgrupo;
    }
    public void setSubgrupo(String subgrupo) {
        this.subgrupo = subgrupo;
    }
    public String getPresencaUrl() {
        return presencaUrl;
    }
    public void setPresencaUrl(String presencaUrl) {
        this.presencaUrl = presencaUrl;
    }
    public String getArquivosUrl() {
        return arquivosUrl;
    }
    public void setArquivosUrl(String arquivosUrl) {
        this.arquivosUrl = arquivosUrl;
    }
    public EvidenciaInfo orElseThrow(Object object) {
        return null;
    }

    //#endregion

}