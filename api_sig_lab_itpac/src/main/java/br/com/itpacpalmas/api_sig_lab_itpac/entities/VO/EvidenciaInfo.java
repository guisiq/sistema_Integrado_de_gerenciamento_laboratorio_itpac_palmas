package br.com.itpacpalmas.api_sig_lab_itpac.entities.VO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EvidenciaInfo {
    
    private long id;
    private Integer idagendamento;
    private String sala;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "GMT-3")
    private LocalDate data;
    private String subgrupo;
    private String nomeAtividade;
    private String codigo;
    private Double horasAprendizagem;
    private String presencaUrl;
    private String arquivosUrl;
    
    //#region ------ Getter e Setter

    public long getId() {
        return id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public Double getHorasAprendizagem() {
        return horasAprendizagem;
    }
    public void setHorasAprendizagem(Double horasAprendizagem) {
        this.horasAprendizagem = horasAprendizagem;
    }
    public String getNomeAtividade() {
        return nomeAtividade;
    }
    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }
    public Integer getIdagendamento() {
        return idagendamento;
    }
    public void setIdagendamento(Integer idagendamento) {
        this.idagendamento = idagendamento;
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