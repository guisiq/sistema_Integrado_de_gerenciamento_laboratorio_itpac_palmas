package br.com.itpacpalmas.api_sig_lab_itpac.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AulaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aluno;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aula;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.Presenca;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.PresencaAluno;
@Service
public class PresencaService {

    @Autowired
    AulaRepository repository;



    public void save(Presenca presenca) {
        Aula aula = repository.findById(presenca.getId()).get();
        List<Aluno> alunosPresentes = new ArrayList<Aluno>();
        presenca.getListaAluno().forEach(p ->{
            if (p.isPresente()) {
                Aluno aux = new Aluno();
                aux.setId(p.getId());
                alunosPresentes.add(aux);
            } 
        });
        aula.setAlunosPresentes(alunosPresentes);
        repository.save(aula);
    }

    public Presenca  findById(int id) {
        Presenca retorno = new Presenca();
        retorno.setId(id);
        Aula aula = repository.findById(id).get();
        List<Aluno> totaisAlunos = aula.getAgendamento().getSubgrupo().getAlunos();
        List<PresencaAluno> presencaAlunos = new ArrayList<>();
        totaisAlunos.forEach(a ->{
            PresencaAluno aux = new PresencaAluno();
            aux.setId(a.getId());
            aux.setNome(a.getPessoa().getNome());
            if (contais(aula.getAlunosPresentes(), a.getId() )) {
                aux.setPresente(true);
            }else{
                aux.setPresente(false);
            }
            presencaAlunos.add(aux);
        });
        retorno.setListaAluno(presencaAlunos);

        return retorno;
    }
    private boolean contais(List<Aluno> alunos,int id){
        for (Aluno aluno : alunos) {
            if (aluno.getId() == id) {
                return  true; 
            }
        }
        return false;
    }
    
}