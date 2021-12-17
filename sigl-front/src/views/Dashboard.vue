<template>
  <v-data-table
    :headers="titulos"
    :items="agendamentos" 
    :search="search"
 
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Dashboard</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Pesquisar"
          single-line
          hide-details
        ></v-text-field>
        <v-spacer></v-spacer>
      </v-toolbar>
    </template>
  </v-data-table>
</template>
<style>
.add {
  width: 40px;
  height: 40px;
}
.template-add {
  padding-top: 1%;
}
.data-table {
  padding: 3%;
}
</style>
<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

//var urlProfessor = "http://api-sig-itpac-84633.herokuapp.com/api/professores";
//var urlDisciplina = "http://api-sig-itpac-84633.herokuapp.com/api/disciplina";
//var urlSala = "http://api-sig-itpac-84633.herokuapp.com/api/sala";
var url = "https://api-sig-itpac-84633.herokuapp.com/api/";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDesativar: false,
    multiple: {
      type: Boolean,
      default: false,
    },
    titulos: [
      { text: "Professor", value: "professor.pessoa.nome",sortable: false},
      { text: "Disciplina", value: "disciplina.nome", sortable: false},
      { text: "Subgrupo", value: "subgrupo.nome", sortable: false },
      { text: "Hora Início", value: "horaInicio", sortable: false },
      { text: "Hora Término", value: "horaFim", sortable: false },
      { text: "Motivo" , value: "motivo" , sortable: false  },
      { text: "Horário" , value: "horario", sortable: false  },
      { text: "Data", value: "data",sortable: false},
      { text: "Sala", value: "sala.nome",sortable: false },
    ],
    agendamentos: [],
    editIndice: -1,
    itemEditado: {
      id: null,
      subgrupo: null,
      descricao: "",
      data: "",
      sala: "",
      profsSelecionados: [],
      ativo: true,
    },
    itemPadrao: {
      id: null,
      subgrupo: null,
      descricao: "",
      data: "",
      sala: "",
      ativo: true,
    },
    profsSelecionados: [],
  }),

  computed: {
    tituloForm() {
      return this.editIndice === -1 ? "Cadastrar Status" : "Editar Status";
    },
  },

  watch: {
    dialog(val) {
      val || this.fechar();
    },
    dialogDesativar(val) {
      val || this.fecharDesativar();
    },
  },

  created() {
    this.inicializar();
  },

  methods: {
    dataFormatada(data){
        let dia  = data.getDate().toString().padStart(2, '0')
        let mes  = (data.getMonth()+1).toString().padStart(2, '0') //+1 pois no getMonth Janeiro começa com zero.
        let ano  = data.getFullYear();
        return dia+"-"+mes+"-"+ano;
    },
    dataFormaHora(data){
        let hora  = data.getDate().toString().padStart(2, '0')
        let minuto  = (data.getMonth()+1).toString().padStart(2, '0') //+1 pois no getMonth Janeiro começa com zero.
        let segundo  = data.getFullYear();
        return hora+":"+minuto+":"+segundo;
    },
    
    inicializar() {
      this.axios
        .get(url+"periodo/Agendamentos/buscarPorDatas/" +this.dataFormatada(new Date) )
        .then((res) => {
          this.agendamentos = res.data
          console.log(res.data);
       
        });
      setInterval(()=>{
        this.axios
          .get(url+"periodo/Agendamentos/buscarPorDatas/" +this.dataFormatada(new Date) )
          .then((res) => {
            this.agendamentos = res.data
            console.log(res.data);
           
          });
      },60000)
    },
    
    
}
}
</script>