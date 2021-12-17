<template>
  <v-data-table
    :headers="titulos"
    :items="disciplinas"
    :search="search"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Gerenciamento de Disciplina</v-toolbar-title>
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
          label="Filtro"
        ></vue-select>
         <v-dialog v-model="dialog" scrollable  width="500px" height="300px"  >
          <template v-slot:activator="{ on, attrs }" class="template-add">
            <!-- <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">Adicionar</v-btn> -->
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
                <v-container>
                  <v-row>
                    <v-col cols="8" sm="6" md="4">
                      <v-text-field
                        v-model="itemEditado.nome"
                        label="Nome"          
                      ></v-text-field>
                    </v-col>

                    <v-col cols="8" sm="6" md="4">
                      <v-text-field
                        v-model="itemEditado.apelido"
                        label="Apelido"
                      ></v-text-field>
                    </v-col>

                    <v-col cols="8" sm="6" md="4" max-heigth="60px">
                      <v-label>Periodo</v-label>
                      <vue-select
                        v-model="periodoSelecionado"
                        :options="periodo"
                        :search="search"
                        label="periodo"
                      ></vue-select>
                    </v-col>

                    <v-col cols="8" sm="6" md="4">
                      <v-label>Professores</v-label>
                      <vue-select  class="select"
                        :getOptionLabel="
                          (professores) => professores.pessoa.nome"
                        v-model="profsSelecionados"
                        :options="professores"
                        label="professores"
                        :search="search"
                        :multiple="true"
                      ></vue-select>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="fechar">
                Cancelar
              </v-btn>
              <v-btn
                small
                color="primary"
                class="mr-4"
                @click="salvar"
                >Salvar</v-btn
              >
            </v-card-actions>
          </v-card>
         </v-dialog>
         

         <v-dialog v-model="dialogDetalhar" max-width="700px">
          <v-simple-table dense>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="align-center"> Disciplina</th>
                  <th class="align-center"> Apelido </th>
                  <th class="align-center"> Periodo</th>
                  <th class="align-center"> Professores</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{{ itemEditado.nome }}</td>
                  <td>{{ itemEditado.apelido }}</td>
                  <td>{{ periodoSelecionado }}</td>
                  <td class="td-lista" v-for="(item, index) in profsSelecionados " :key="index">{{ item }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
         </v-dialog>

         <v-dialog v-model="dialogDesativar" max-width="400px">
          <v-card class="card-modal">
            <v-card-title class="text-h6"
              >Deseja {{ mudarStatus }} esta disciplina?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="fecharDesativar"
                >Não</v-btn
              >
              <v-btn small color="primary" dark @click="desativeItemConfirm"
                >Sim</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
         </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.acoes`]="{ item }">
      <v-icon small class="mr-2" @click="detalharItem(item)" color="brown">mdi-eye</v-icon>
      <v-icon small class="mr-2" @click="editItem(item)" color="blue">mdi-pencil</v-icon>
      <v-icon small @click="desativeItem(item)" color="red">mdi-power-standby</v-icon>
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
.td-lista {
  padding: 2px;
  display: block;
}
</style>
<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

var url = "https://api-sig-itpac-84633.herokuapp.com/api/disciplina";
var urlPeriodo = "https://api-sig-itpac-84633.herokuapp.com/api/periodo";
var urlProfessor = "https://api-sig-itpac-84633.herokuapp.com/api/professores";
var urlPatch =
  "https://api-sig-itpac-84633.herokuapp.com/api/disciplina/desativar/";
var urlDispatch =
  "https://api-sig-itpac-84633.herokuapp.com/api/disciplina/ativar/";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDesativar: false,
    dialogDetalhar: false,
    multiple: {
      type: Boolean,
      default: false,
    },
    titulos: [
      { text: "Disciplina", value: "nome" },
      {
        text: "Apelido",
        align: "start",
        value: "apelido",
      },
      { text: "Professor", value: "professores", sortable: false },
      { text: "Período", value: "periodo.periodo",sortable: false },
      { text: "Status", value: "ativo",sortable: false },
      { text: "Ações", value: "acoes",sortable: false},
    ],
    disciplinas: [],
    periodo: [],
    periodosRaw: [],
    professores: [],
    profsRaw: [],
    editIndice: -1,
    filtros: ["Ativados", "Todos"],
    filtroSelecionado: "Ativados",
    itemEditado: {
      id: null,
      nome: "",
      apelido: "",
      periodoSelecionado: null,
      profsSelecionados: [],
      ativo: true,
    },
    itemPadrao: {
      id: null,
      nome: "",
      apelido: "",
      ativo: null,
    },
    profsSelecionados: [],
    periodoSelecionado: null,
  }),

  computed: {
    tituloForm() {
      return this.editIndice === -1 ? "Cadastrar disciplina" : "Editar Dados";
    },
    mudarStatus() {
      return this.itemEditado.ativo == "Ativado" ? "desativar " : "ativar ";
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

  mounted() {
    this.inicializar();
  },

  methods: {
    async inicializar() {
      this.axios
        .get(url+"/getAll/false", this.disciplinas)
        .then((res) => {
          console.log(res.data);
          this.disciplinas = res.data.map((p) => ({
            ...p,
            ativo: p.ativo ? "Ativado" : "Desativado",
            professores: p.professores
              .map((p) => p.pessoa.nome),
          }));
        })
        .catch((error) => {
          console.warn(error);
        });
      await Promise.all([this.getProfessores(), this.getPeriodos()]);
    },

    redefinirTable() {
      if (this.filtroSelecionado === "Todos") {
       this.inicializar();
      } else {
        axios.get(url + "/getAll/true", this.disciplinas).then((res) => {
          this.disciplinas = res.data.map((p) => {
            p.ativo = p.ativo ? "Ativado" : "Desativado";
            return p;
          });
          console.log(res.data);
        });
      }
    },

    reloadPage() {
      window.location.reload();
    },

    async getPeriodos() {
      const { data } = await this.axios.get(urlPeriodo+"/getAll/true");
      this.periodosRaw = data;
      this.periodo = data
        .filter((d) => d.periodo)
        .map((d) => ({ ...d, idperiodo: d.id }));
    },

    async getProfessores() {
      const { data } = await this.axios.get(urlProfessor+"/getAll/true");
      this.profsRaw = data;
      this.professores = data.filter((d) => d.pessoa.nome);
    },

    detalharItem(item) {
      this.editIndice = this.disciplinas.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var id = this.itemEditado.id;
      axios.get(url + "/" + id).then((res) => {
        this.itemEditado = res.data;
        this.periodoSelecionado = this.itemEditado.periodo.periodo;
        this.profsSelecionados = this.itemEditado.professores.map((d) => d.pessoa.nome);
      });

      this.dialogDetalhar = true;
    },

    editItem(item) {
      this.editIndice = this.disciplinas.indexOf(item); 
      this.itemEditado = Object.assign({}, item);
      var id = this.itemEditado.id;
      axios.get(url + "/" + id).then((res) => {
        this.itemEditado = res.data;
        this.profsSelecionados = this.itemEditado.professores;
        console.log("prof", this.profsSelecionados);
        this.periodoSelecionado = this.itemEditado.periodo;
        console.log("periodooo", this.periodoSelecionado);
      });
      this.dialog = true;
    },

    desativeItem(item) {
      this.editIndice = this.disciplinas.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogDesativar = true;
    },

    desativeItemConfirm() {
      if (this.itemEditado.ativo == "Ativado") {
        axios
          .patch(urlPatch + this.itemEditado.id, {
            ativo: false,
          })
          .then((res) => {
            console.log(res.data);
            alert("A disciplina foi desativada com sucesso !");
           // this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        axios
          .patch(urlDispatch + this.itemEditado.id, {
            ativo: true,
          })
          .then((res) => {
            console.log(res.data);
            alert("A disciplina foi ativado com sucesso !");
           // this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
      }
      this.inicializar();
      this.fecharDesativar();
    },

    fechar() {
      this.dialog = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
        this.profsSelecionados = "";
            this.periodoSelecionado ="";
      });
    },

    fecharDesativar() {
      this.dialogDesativar = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
      });
    },

    salvar() {
      if (this.editIndice > -1) {
        axios
          .put(url, {
            id: this.itemEditado.id,
            nome: this.itemEditado.nome,
            apelido: this.itemEditado.apelido,
            ativo: this.itemEditado.ativo,
            periodo: this.periodoSelecionado,
            professores: this.profsSelecionados,
          })
          .then((res) => {
            alert("Os dados foram atualizados com sucesso !");
            this.profsSelecionados = "";
            this.periodoSelecionado ="";
            console.log(res.data);
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
        Object.assign(this.disciplinas[this.editIndice], this.itemEditado);
      } else {
        axios
          .post(url, {
            nome: this.itemEditado.nome,
            ativo: true,
            apelido: this.itemEditado.apelido,
            professores: this.profsSelecionados,
            periodo: {
              id: this.periodoSelecionado.idperiodo,
            },
          })
          .then((res) => {
            this.disciplinas = res.data;
            alert("Os dados foram adicionados com sucesso !");
            this.profsSelecionados = "";
            this.periodoSelecionado ="";
            console.log(res.data);
            this.reloadPage(); 
          })
          .catch((error) => {
            console.log(error);
          });
        this.disciplinas.push(this.itemEditado);
      }
      this.fechar();
      this.inicializar();
    },
  },
};
</script>