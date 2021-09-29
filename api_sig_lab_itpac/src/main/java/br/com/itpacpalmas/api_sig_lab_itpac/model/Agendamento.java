package br.com.itpacpalmas.api_sig_lab_itpac.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String motivo;
    private Manual manual;
    private Professor professor;
    private Sala sala;
    private Status Status; 
    private Subgrupo subgrupo; 
    private Date data; 
}
