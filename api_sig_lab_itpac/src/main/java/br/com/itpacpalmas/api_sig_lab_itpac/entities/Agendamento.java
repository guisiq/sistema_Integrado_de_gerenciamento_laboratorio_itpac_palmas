package br.com.itpacpalmas.api_sig_lab_itpac.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


import javax.persistence.GenerationType;
@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idagendamento")
    private Integer id;
    
    private String motivo;
    @ManyToOne
    @JoinColumn(name = "manual")
    private Manual manual;
    @ManyToOne
    @JoinColumn(name = "professor")
    private Professor professor;
    @ManyToOne
    @JoinColumn(name = "sala")
    @Column(nullable = false)
    private Sala sala;
    @ManyToOne
    @JoinColumn(name = "status")
    @Column(nullable = false)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "diciplina")
    private Disciplina diciplina;
    @ManyToOne
    @JoinColumn(name = "subgrupo") 
    @Column(nullable = false)
    private Subgrupo subgrupo; 
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-3")
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;


    private boolean ativo;

    public Disciplina getDiciplina() {
        return diciplina;
    }

    public void setDiciplina(Disciplina diciplina) {
        this.diciplina = diciplina;
    }

    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public Manual getManual() {
        return manual;
    }
    public void setManual(Manual manual) {
        this.manual = manual;
    }
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public Sala getSala() {
        return sala;
    }
    public void setSala(Sala sala) {
        this.sala = sala;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Subgrupo getSubgrupo() {
        return subgrupo;
    }
    public void setSubgrupo(Subgrupo subgrupo) {
        this.subgrupo = subgrupo;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    } 
    
}
/*"manual" : {"id" : 1},
"professor" : {"id" : 1},
"sala" : {"id" : 1},
"status" : {"id" : 1},
"subgrupo" : {"id" : 1},
"data" : {"id" : 1},*/