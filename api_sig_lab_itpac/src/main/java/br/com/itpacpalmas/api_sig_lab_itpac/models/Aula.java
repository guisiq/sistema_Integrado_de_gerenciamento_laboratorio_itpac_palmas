package br.com.itpacpalmas.api_sig_lab_itpac.models;

public class Aula{
    private Integer idAula;
    private String codigo;
    private String nome_atividade;
    private Double horas_aprendizagem;
    private Agendamento agendamento;
   
    public Agendamento getAgendamento() {
        return agendamento;
    }
    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
    public Double getHoras_aprendizagem() {
        return horas_aprendizagem;
    }
    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNome_atividade() {
        return nome_atividade;
    }
    public void setNome_atividade(String nome_atividade) {
        this.nome_atividade = nome_atividade;
    }
    public void setHoras_aprendizagem(Double horas_aprendizagem) {
        this.horas_aprendizagem = horas_aprendizagem;
    }
}
