<template>
  <v-data-table
    :headers="titulos"
    :items="agendamentos"
    :search="search"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Agendamento de Laboratório - Aluno</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <!-- <v-ster></v-ster> -->
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Pesquisar"
          single-line
          hide-details
        ></v-text-field>
        <v-spacer></v-spacer> 
        <vue-select
          @input="redefinirTable" 
          v-model="filtroSelecionado"
          :options="filtros"
          :rules="['Campo Obrigatorio']"
          :search="search"
          label="Filtro"
        ></vue-select>
        <!-- criar/editar agendamento -->
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }" class="template-add">
            <!-- <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">Adicionar</v-btn> -->
            <v-btn
              small
              class="mx-2 add"
              fab
              dark
              color="blue"
              @click="criarSubgrupo"
              ><v-icon dark>mdi-account</v-icon></v-btn
            >

            <v-btn
              small
              class="mx-2 add"
              fab
              dark
              color="green"
              v-bind="attrs"
              v-on="on"
              ><v-icon dark> mdi-plus</v-icon></v-btn
            >
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ tituloForm }}</span>
            </v-card-title>

            <v-card-text>
              <v-form>
                <v-row>
                  <!-- data -->
                  <v-col cols="11" sm="5">
                    <v-text-field
                      v-model="itemEditado.date"
                      label="Data"
                      v-mask="'####-##-##'"
                      type="date"
                      required
                    ></v-text-field>
                  </v-col>
                  <!-- hora inicio -->
                  <v-col cols="11" sm="5">
                    <v-text-field
                      v-model="itemEditado.horaInicio"
                      label="Hora de Início"
                      type="time"
                      required
                    ></v-text-field>
                  </v-col>
                  <!-- hora termino -->
                  <v-col cols="11" sm="5">
                    <v-text-field
                      v-model="itemEditado.horaFim"
                      label="Hora de Término"
                      type="time"
                      required
                    ></v-text-field>
                  </v-col>
                  <!-- motivo -->
                  <v-col cols="11" sm="5">
                    <v-text-field
                      v-model="itemEditado.motivo"
                      label="Motivo"
                      :rules="[(v) => !!v || 'Campo Obrigatório']"
                      required
                    ></v-text-field>
                  </v-col>
                 <!-- manual -->
                  <v-col cols="8" sm="6" md="4">
                    <v-label>Manual</v-label>
                    <vue-select
                      :getOptionLabel="(manual) => manual.descricao"
                      v-model="manualSelecionado"
                      :options="manuais"
                      :rules="['Campo Obrigatório']"
                      :search="search"
                      label="Manual"
                      required = true
                    ></vue-select>
                  </v-col>
                  <!-- Sala -->
                  <v-col cols="8" sm="6" md="4">
                    <v-label>Sala</v-label>
                    <vue-select
                      :getOptionLabel="(sala) => sala.nome"
                      v-model="salaSelecionada"
                      :options="salas"
                      :rules="['Campo Obrigatório']"
                      :search="search"
                      label="Sala"
                      required = true
                    ></vue-select>
                  </v-col>
                  <!-- professor -->
                  <v-col cols="8" sm="6" md="4">
                    <v-label>Professor</v-label>
                    <vue-select
                      :getOptionLabel="(professor) => professor.pessoa.nome"
                      v-model="profSelecionado"
                      :options="professores"
                      :rules="['Campo Obrigatório']"
                      :search="search"
                      label="Professor"
                      required =true
                    ></vue-select>
                  </v-col>
                  <!-- diciplina -->
                  <v-col cols="8" sm="6" md="4" >
                    <v-label>Disciplina</v-label>
                    <vue-select
                      :getOptionLabel="(diciplina) => diciplina.nome"
                      v-model="disciplinaSelecionada"
                      :options="disciplinas"
                      :search="search"
                      label="Diciplina"
                    ></vue-select>
                  </v-col>
                  <!-- subgrupo -->
                  <v-col cols="8" sm="6" md="4" >
                    <v-label>Subgrupo</v-label>
                    <vue-select
                      :getOptionLabel="(subgrupo) => subgrupo.nome"
                      v-model="subgrupoSelecionado"
                      :options="subgrupos"
                      :search="search"
                      label="Subgrupo"
                    ></vue-select>
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="fechar">Cancelar</v-btn>
              <v-btn small color="primary" class="mr-4" @click="salvar"
                >Salvar</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
        <!-- ativar/desativar  -->
        <v-dialog v-model="dialogDesativar" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Deseja {{ mudarStatus }} este agendamento?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="fecharDesativar">
                Não</v-btn
              >
              <v-btn small color="primary" dark @click="desativeItemConfirm"
                >Sim</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <!--dialog para criar subgrupo na hora do agendamento -->
     <v-dialog v-model="dialogSubgrupo" max-width="600px">
          <v-card>
            <v-card-title>
              <span class="text-h5">Cadastrar Subgrupo</span>
            </v-card-title>
            <v-card-text>
              <v-form>
                <v-container>
                  <v-row>
                    <v-col cols="8" sm="6" md="4">
                      <v-text-field
                        v-model="itemEditado.nome"
                        label="Nome"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="8" sm="6" md="4">
                      <v-label>Professor</v-label>
                      <vue-select
                        v-model="profSubgrupo"
                        :getOptionLabel="(profSubgrupo) => profSubgrupo.pessoa.nome"
                        :options="profsSub"
                        :search="search"
                        label="professor"
                        required
                      ></vue-select> 
                    </v-col>
                    <v-col cols="8" sm="6" md="4">
                      <v-label>Alunos</v-label>
                      <vue-select
                        v-model="alunosSelecionados"
                        :getOptionLabel="(alunos) => alunos.pessoa.nome"
                        :options="alunos"
                        label="aluno"
                        :multiple="true"
                        required
                      ></vue-select>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="dialogSubgrupo = false">
                Cancelar
              </v-btn>
              <v-btn small color="primary" class="mr-4" @click="salvarSubgrupo"
                >Salvar</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog> 

        <v-dialog v-model="dialogDetalhar" max-width="500px">
          <!-- <v-card class="mx-auto"> -->
          <v-simple-table dense>
            <template v-slot:default>
              <thead>
                <tr>
                  <th>Sala</th>
                  <th>Data</th>
                  <th>Hora de Início</th>
                  <th>Hora de Término</th>
                  <th>Subgrupo</th>
                  <th>Manual</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{{ itemEditado.sala.nome}}</td>
                  <td>{{ itemEditado.date }}</td>
                  <td>{{ itemEditado.horaInicio}}</td>
                  <td>{{ itemEditado.horaFim}}</td>
                   <td>{{ itemEditado.subgrupo.nome}}</td>
                   <td>{{ itemEditado.manual.descricao }}</td>
                   <td>{{ itemEditado.status.descricao }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.acoes`]="{ item }">
      <v-icon small class="mr-2" @click="detalharItem(item)" color="brown"
        >mdi-eye</v-icon
      >
      <v-icon small class="mr-2" @click="editItem(item)" color="blue">
        mdi-pencil
      </v-icon>
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
.check {
  text-align: left;
  align-items: left;
}

</style>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";

Vue.use(VueAxios, axios);

var url = "https://api-sig-itpac-84633.herokuapp.com/api/";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDesativar: false,
    dialogDetalhar: false,
    dialogSubgrupo: false,
    statusSelecionado:null,
    titulos: [
      { text: "Sala", value: "sala.nome", sortable: false },
      { text: "Data", value: "data", sortable: false,formatter:"dataFormatada" },
      { text: "Hora Início", value: "horaInicio", sortable: false },
      { text: "Hora Término", value: "horaFim", sortable: false },
      { text: "Motivo", value: "motivo", sortable: false },
      { text: "Subgrupo", value: "subgrupo.nome", sortable: false },
      { text: "Manual", value: "manual.descricao", sortable: false },
      { text: "Status", value: "status.descricao", sortable: false },
      { text: "Ações", value: "acoes", sortable: false },
    ],
    select: [],
    dataInicio:null,
    datafim:null,
    items: [
      "Domingo",
      "Segunda-feira",
      "Terça-feira",
      "Quarta-feira",
      "Quinta-feira",
      "Sexta-feira",
      "Sábado",
    ],
    date: new Date(new Date().getTimezoneOffset() * 60000)
      .toISOString()
      .substr(0, 10),
    profsSub: [],
    profSubgrupo: null,  
    subgrupo: [],
    manual: [],
    sala: [],
    professor: [],
    disciplina: [],
    subgrupos: [],
    Lstatus:[],
    manuais:[],
    salas:[],
    professores:[],
    disciplinas:[],
    agendamentos: [],
    alunos:[],
    editIndice: -1,
    subgrupoSelecionado: null,
    manualSelecionado: null,
    salaSelecionada: null,
    disciplinaSelecionada: null,
    profSelecionado: null,
    alunosSelecionados: [],
    itemEditado: {
      id: null,
      motivo: "",
      date: null,
      horaInicio: null,
      horaFim: null,
      status: null,
      ativo: true,
    },
    itemPadrao: {
      id: null,
      motivo: "",
      date: null,
      horaInicio: null,
      horaFim: null,
      status: null,
      ativo: true,
    },
  }),

  computed: {
    tituloForm() {
      return this.editIndice === -1
        ? "Cadastrar Agendamento"
        : "Editar Agendamento";
    },
    mudarStatus() {
      return this.itemEditado.status == "Pendente" ? "aprovar " : "cancelar ";
    },
  },

  watch: {
    dialog(val) {
      val || this.fechar();
    },
  },

  mounted() {
    this.inicializar();
  },

  methods: {
    dataFormatada(data){
        let dia  = data.getDate().toString().padStart(2, '0')
        let mes  = (data.getMonth()+1).toString().padStart(2, '0') //+1 pois no getMonth Janeiro começa com zero.
        let ano  = data.getFullYear();
        return dia+"/"+mes+"/"+ano;
    },
    async inicializar() {
      this.axios
        .get(url + "periodo/Agendamentos", this.agendamentos)
        .then((res) => {
          this.agendamentos = res.data;
          console.log(res.data);
        });
     await Promise.all([this.getSubgrupos(), this.getManuais(),this.getSalas(),this.getProfessores(),this.getDisciplinas(),this.getStatus()]);

    },

    reloadPage() {
      window.location.reload();
    },
    
    redefinirTable(){
        this.axios.get(url + "periodo/Agendamentos/getAll/false", this.agendamentos).then((res) => {
        this.agendamentos = res.data;
      });
    },

   async getSubgrupos() {
      const { data } = await this.axios.get(url + "subgrupo/getAll/false");
      this.subgrupos = data.filter(Boolean);
    },
    async getStatus() {
      const { data } = await this.axios.get(url + "status/getAll/false");
      this.Lstatus = data;
      //console.log("data",data)
    },

    async getManuais() {
      const { data } = await this.axios.get(url + "manual");
      this.manuais = data.filter(Boolean);
    },

    async getDisciplinas() {
      const { data } = await this.axios.get(url + "disciplina/getAll/false");
      this.disciplinas = data.filter(Boolean);
    },

    async getSalas() {
      const { data } = await this.axios.get(url + "sala");
      this.salas = data.filter(Boolean);
    },

    async getProfessores() {
      const { data } = await this.axios.get(url + "professores/getAll/false");
      this.professores = data.filter(Boolean);
    },

   getAlunos() {
      this.axios
        .get(url + "aluno/getAll/true")
        .then((res) => {
          this.alunos = res.data;
          console.log(res.data,"alunos subgrupo");
        });
    },

    getProfs() {
      this.axios
        .get(url + "professores/getAll/true")
        .then((res) => {
          this.profsSub = res.data;
          console.log(res.data);
        });
      //this.alunos = data.filter((d) => d.pessoa.nome).filter(Boolean);
      console.log(this.profsSub,"profs subgrupo")
    },


    detalharItem(item) {
      this.editIndice = this.agendamentos.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var id = this.itemEditado.id;
      axios.get(url + "periodo/Agendamentos/" + id).then((res) => {
           this.itemEditado = res.data;
           this.salaSelecionada = res.data.filter((d) => d.sala.nome);
           console.log(this.itemEditado,"Objetooo")
           console.log(this.salaSelecionada,"Sala")
      //   this.profSelecionado = this.itemEditado.professor;
      });
      this.dialogDetalhar = true;
    },

    editItem(item) {
      this.editIndice = this.agendamentos.indexOf(item);
      this.itemEditado = item;
      this.statusSelecionado = this.itemEditado.status;
      this.manualSelecionado = this.itemEditado.manual;
      this.salaSelecionada = this.itemEditado.sala;
      this.disciplinaSelecionada = this.itemEditado.disciplina;
      this.subgrupoSelecionado = this.itemEditado.subgrupo;
      this.profSelecionado = this.itemEditado.professor;
      
      this.dialog = true;
    },

  criarSubgrupo() {
      this.dialogSubgrupo = true;
      this.getAlunos();
      this.getProfs();
    },

  salvarSubgrupo() {
      axios
        .post(url+"subgrupo", {
          nome: this.itemEditado.nome,
          ativo: this.itemEditado.ativo,
          profsSub: {
            id: this.profSubgrupo,
          },
          alunos: this.alunosSelecionados,
        })
        .then((res) => {
          this.subgrupos = res.data;
          console.log(res.data);
          alert("Os dados foram adicionados com sucesso !");
          this.profSubgrupo = "";
          this.alunosSelecionados = "";
          this.reloadPage();
        })
        .catch((error) => {
          console.log(error);
        });
      this.subgrupos.push(this.itemEditado);
      this.fechar();
    },

    fechar() {
      this.dialog = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
      });
    },

    salvar() {
      if (this.editIndice > -1) {
        const request = {
              status:this.statusSelecionado,
              id: this.itemEditado.id,
              motivo: this.itemEditado.motivo,
              data: this.itemEditado.date,
              ativo: true,
              horaInicio: this.itemEditado.horaInicio,
              horaFim: this.itemEditado.horaFim,
              manual: this.manualSelecionado,
              professor: this.profSelecionado,
              sala: this.salaSelecionada,
              subgrupo: this.subgrupoSelecionado,
            };
        console.log("request",request)
        let endpoint = "periodo/Agendamentos" 
        axios
          .put(url+endpoint,request )
          .then((res) => {
            //this.salas = res.data;
            alert("Os dados foram atualizados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });

        Object.assign(this.evidencias[this.editIndice], this.itemEditado);
      } else {
        let endpoint = "periodo/Agendamentos/aluno" 
        const request = {
              //id: this.itemEditado.id,
              status:this.statusSelecionado,
              motivo: this.itemEditado.motivo,
              data: this.itemEditado.date,
              ativo: true,
              horaInicio: this.itemEditado.horaInicio,
              horaFim: this.itemEditado.horaFim,
              manual: this.manualSelecionado,
              professor: this.profSelecionado,
              disciplina: this.disciplinaSelecionada,
              sala: this.salaSelecionada,
              subgrupo: this.subgrupoSelecionado,
              };
        console.log("request",request)
        axios
          .post(url+endpoint,request)
          .then((res) => {
            // this.agendamentos = res.data;
            alert("Os dados foram adicionados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          });

        this.agendamentos.push(this.itemEditado);
      }
      this.fechar();
    },
  },
};
</script>