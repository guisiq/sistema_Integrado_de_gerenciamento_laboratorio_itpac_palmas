package br.com.itpacpalmas.api_sig_lab_itpac.model;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import br.com.itpacpalmas.api_sig_lab_itpac.repository.*;
import br.com.itpacpalmas.api_sig_lab_itpac.model.Aluno;
import br.com.itpacpalmas.api_sig_lab_itpac.model.Subgrupo;

@Service
public class percistenciaPi {
	@Autowired
	static SubgrupoRepository sRepository;
	@Bean
	public void persistir() throws FileNotFoundException {
		// TODO Auto-generated method stub
		Periodo periodo = new Periodo();

		List<Disciplina> materias = new ArrayList<Disciplina>();
		List<Subgrupo> subgrupos = new ArrayList<Subgrupo>();
		List<String[]> tabela = new ArrayList<String[]>();

		try {
			FileReader arq = new FileReader("E:\\estudos\\PI\\sistema_Integrado_de_gerenciamento_laboratorio_itpac_palmas\\api_sig_lab_itpac\\src\\main\\java\\br\\com\\itpacpalmas\\api_sig_lab_itpac\\model\\Lista dos alunos matriculados em HAM 1 ate HAM 8.csv");
			BufferedReader lerArq = new BufferedReader(arq);
			//pulando o cabecario 
			String linha = lerArq.readLine(); 
			linha = lerArq.readLine(); // l� da segunda at� a �ltima linha 
			while (linha != null) { 
				String[] colunas = linha.split(";") ;
				/*
				if(colunas[6]!=""){

					tabela.add(colunas);
				}*/
				tabela.add(colunas);
				linha = lerArq.readLine(); // l� da segunda at� a �ltima linha 
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
				e.getMessage());
		}
		
		tabela.removeIf(l -> l[5]=="" );
		System.out.println("teste");

		Disciplina materia = new Disciplina();
		materia.setNome(tabela.get(0)[3]);
		
		Aluno aluno = new Aluno();
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(tabela.get(0)[6]);
		aluno.setPessoa(pessoa);
		aluno.setMatricula(tabela.get(0)[5]);

		Subgrupo subgrupo = new Subgrupo();
		subgrupo.setNome(tabela.get(0)[4]);
		List alunosSubgrupo = new ArrayList<Aluno>();
		alunosSubgrupo.add(aluno);
		subgrupo.setAlunos(alunosSubgrupo);
		subgrupo.setDisciplina(materia);

		subgrupos.add(subgrupo);
		materias.add(materia);

		for (int i = 1; i < tabela.size(); i++) {
			aluno = new Aluno();
			pessoa = new Pessoa();
			pessoa.setNome(tabela.get(i)[6]);
			aluno.setPessoa(pessoa);
			aluno.setMatricula(tabela.get(i)[5]);
			alunosSubgrupo.add(aluno);
			
			if (!tabela.get(i)[3].equals(materias.get( materias.size()-1).getNome())) {
				materia = new Disciplina();
				materia.setNome(tabela.get(i)[3]);
				materias.add(materia);
			}
			if (!tabela.get(i)[4].equals(subgrupos.get(subgrupos.size()-1).getNome())) {
				subgrupo = new Subgrupo();
				subgrupo.setNome(tabela.get(i)[4]);
				alunosSubgrupo = new ArrayList<Aluno>();
				subgrupo.setAlunos(alunosSubgrupo);
				subgrupo.setDisciplina(materia);
				subgrupos.add(subgrupo);
			}
		}
		List<Subgrupo> save = sRepository.saveAll(subgrupos);

		System.out.println("teste");
	}

}
