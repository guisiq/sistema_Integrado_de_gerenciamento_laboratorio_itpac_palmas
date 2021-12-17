<template>
  <section
    id="about"
    class="overflow-hidden"
  >
    <v-row
      class=""
      no-gutters
    >
      <v-col
        class="pa-5"
        cols="12"
        md="6"
      >
        <base-bubble-1 style="transform: translate(5%, -5%)" />

        <!-- <base-heading class="black">
          <li><strong>Sobre nós</strong></li>
        </base-heading> -->

        <ul>
            <li><strong>Projeto Sistema Gerencial de Laboratório</strong></li>
        </ul>
    
        <div class="text1">
            <p>
                Através de parceria entre ITPAC e Unitins, foi proposto o desenvolvimento de um software que permita o gerenciamento das aulas de laboratório. 
                Sendo assim, deve abranger os cursos disponíveis, relatórios e painéis de controle. Tendo como principal 
                intuito o fornecimento das informações coletadas às organizações internacionais para que tenham ciência das ações realizadas no centro de simulação.
                Partindo do exposto, foram designados os alunos do quarto período de Sistemas de Informação para desenvolver este sistema por intermédio da disciplina Projeto Integrador I
                e orientação dos professores Frederico Pires, Fredson Costa e Itamar Galvão.
            </p>
        </div>


       <p>
           Através de parceria entre ITPAC e Unitins, foi proposto o desenvolvimento de um software que permita o gerenciamento das aulas de laboratório. 
            Sendo assim, deve abranger os cursos disponíveis, relatórios e painéis de controle. Tendo como principal 
            intuito o fornecimento das informações coletadas às organizações internacionais para que tenham ciência das ações realizadas no centro de simulação.
            Partindo do exposto, foram designados os alunos do quarto período de Sistemas de Informação para desenvolver este sistema por intermédio da disciplina Projeto Integrador I
            e orientação dos professores Frederico Pires, Fredson Costa e Itamar Gonçalves.

       </p>
        

        <base-text class="mb-5">
        <div class="text2">
            <ul>
                <li><strong>Alunos</strong></li>
                <li>Aldir Lyra Aguiar Neto</li>
                <li>Alessandro Gabriel Onorato</li>
                <li>Alyne Lima Ribeiro</li>
                <li>Anthony Silveira dos Santos</li>
                <li>Davi Noleto Perna Jordão de Souza</li>
                <li>Débora Ribeiro Pereira</li>
                <li>Guilherme Henrique Siqueira Lopes</li>
                <li>Jhemerson Conceição Santos</li>
                <li>Luana Martins Macedo</li>
                <li>Lucas José da Silva</li>
                <li>Lorenzo Lima Pedrosa</li>
                <li>Luiz Filipe Luz Rocha</li>
                <li>Luiz Nicolau Lemos Freitas</li>
                <li>Weder Cardoso Tito</li>
            </ul>
        </div>
        </base-text>

        
      </v-col>
        
      <v-col
        class="hidden-sm-and-down"
        md="6"
      >
        <v-img
          :src="require('@/assets/img/aboutme.png')"
          height="100%"
        />
      </v-col>
    </v-row>
  </section>
</template>

<style scoped>

.text1{
    padding-bottom: 50px;
}
.text2{
    padding-bottom: 50px;
    padding-left: 23px;
}

li {
    list-style: none;
}

p {
    text-align: justify;
}


</style>
