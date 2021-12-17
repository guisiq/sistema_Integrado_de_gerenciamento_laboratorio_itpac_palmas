<template>
  <v-data-table
    :headers="titulos"
    :items="salas"
    :search="search"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Gerenciamento de Sala</v-toolbar-title>
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
        <v-dialog v-model="dialog" max-width="500px">
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
            <p v-if="errors.length">
              <b>Por favor, corrija o(s) seguinte(s) erro(s):</b>
              <ul>
                <li v-for="error in errors" :key="error">{{ error }}</li>
              </ul>
           </p>
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
                        v-model="itemEditado.local"
                        label="Local"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="8" sm="6" md="4">
                      <v-text-field
                        v-model="itemEditado.capacidade"
                        label="Capacidade" 
                        type="number"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="8" sm="6" md="4">
                      <v-text-field
                        v-model="itemEditado.descricao"
                        label="Descrição"
                      ></v-text-field>
                    </v-col>   
                  </v-row>
                </v-container>
                   </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="fechar">Cancelar</v-btn>
             <v-btn small color="primary" class="mr-4" @click="checkForm">Salvar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
         <v-dialog v-model="dialogDetalhar" max-width="700px">
          <v-simple-table dense>
  <thead>
    <tr>
      <th class="align-left">Nome</th>
      <th class="align-left">Local</th>
      <th class="align-left">Capacidade</th>
      <th class="align-left">Descrição</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>{{ itemEditado.nome }}</td>
      <td>{{ itemEditado.local }}</td>
      <td>{{ itemEditado.capacidade }}</td>
      <td>{{ itemEditado.descricao }}</td>
    </tr>
  </tbody>
          </v-simple-table>
        </v-dialog>

        <v-dialog v-model="dialogDesativar" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Deseja {{mudarStatus}} esta sala?</v-card-title
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
      </v-toolbar>
    </template>
    <template v-slot:[`item.acoes`]="{ item }">
      <v-icon small class="mr-2" @click="detalharItem(item)" color="brown">mdi-eye</v-icon>
      <v-icon small class="mr-2" @click="editItem(item)" color="blue"> mdi-pencil </v-icon>
      <v-icon small @click="desativeItem(item)" color="red"> mdi-power-standby </v-icon>
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
import { userKey } from "@/global";
Vue.use(VueAxios, axios);

var url = "https://api-sig-itpac-84633.herokuapp.com/api/sala";
// var urlPatch = "http://api-sig-itpac-84633.herokuapp.com/api/sala/desativar/";
// var urlDispatch = "http://api-sig-itpac-84633.herokuapp.com/api/sala/ativar/";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDesativar: false,
    dialogDetalhar: false,
    titulos: [
      { text: "Sala", value: "nome" },
      { text: "Local", value: "local" },
      { text: "Capacidade", value: "capacidade" },
      { text: "Descrição", value: "descricao" },
      { text: "Status", value: "ativo" },
      { text: "Ações", value: "acoes" },
    ],
    errors: [],
    salas: [],
    editIndice: -1,
    filtros: ["Ativados", "Todos"],
    filtroSelecionado: "Ativados",
    itemEditado: {
      id: null,
      nome: "",
      local: "",
      capacidade: null,
      descricao: "",
      ativo: true,
    },
    itemPadrao: {
      id: null,
      nome: "",
      local: "",
      capacidade: null,
      descricao: "",
      ativo: true,
    },
  }),

  computed: {
    tituloForm() {
      return this.editIndice === -1 ? "Cadastrar Sala" : "Editar Sala";
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
    inicializar() {
      const json = localStorage.getItem(userKey);
      const jwt = JSON.parse(json);
      console.log(jwt.token);

       var config = {
        headers: { "Authorization": ' bearer '+ jwt.token },
      };

      this.axios.get(url + "/getAll/false", this.salas, config).then((res) => {
        this.salas = res.data.map((p) => {
          p.ativo = p.ativo ? "Ativado" : "Desativado";
          return p;
        });
        console.log(res.data);
      });
    },

    redefinirTable() {
      if (this.filtroSelecionado === "Todos") {
        this.inicializar();
      } else {
      
      const json = localStorage.getItem(userKey);
      const jwt = JSON.parse(json);
      console.log(jwt.token);

       var config = {
        headers: { "Authorization": ' bearer '+ jwt.token },
      };

        axios.get(url + "/getAll/true", this.salas,config).then((res) => {
          this.salas = res.data.map((p) => {
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

    editItem(item) {
      this.editIndice = this.salas.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialog = true;
    },

    desativeItem(item) {
      this.editIndice = this.salas.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogDesativar = true;
    },

    checkForm() {
      if (this.itemEditado.nome && this.itemEditado.local) {
        this.salvar();
        return true;
      }
      this.errors = [];

      if (!this.itemEditado.nome) {
        this.errors.push("O nome é obrigatório.");
      }
      if (!this.itemEditado.local) {
        this.errors.push("O local é obrigatório.");
      }
    },

    desativeItemConfirm() {
      if (this.itemEditado.ativo == "Ativado") {
         const json = localStorage.getItem(userKey);
      const jwt = JSON.parse(json);
      console.log(jwt.token);

       var config = {
        headers: { "Authorization": 'Bearer'+ jwt.token },
      };

        axios
          .patch(url + "/desativar/" + this.itemEditado.id,config, {
            ativo: false,
          })
          .then((res) => {
            this.salas = res.data;
            console.log(res.data);
            alert("Esta sala foi desativada com sucesso !");
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        axios
          .patch(url + "/ativar/" + this.itemEditado.id,config, {
            ativo: true,
          })
          .then((res) => {
            console.log(res.data);
            alert("Esta sala foi ativada com sucesso !");
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
      }

      this.fecharDesativar();
    },

    fechar() {
      this.dialog = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
      });
    },

    fecharDesativar() {
      this.dialogDesativar = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
      });
    },

    detalharItem(item) {
      this.editIndice = this.salas.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var id = this.itemEditado.id;
      axios.get(url + "/" + id).then((res) => {
        this.itemEditado = res.data;
      });

      this.dialogDetalhar = true;
    },

    salvar() {
      if (this.editIndice > -1) {

      const json = localStorage.getItem(userKey);
      const jwt = JSON.parse(json);
      console.log(jwt.token);

       var config = {
        headers: { "Authorization": ' bearer '+ jwt.token },
      };

        axios
          .put(url,config, {
            id: this.itemEditado.id,
            nome: this.itemEditado.nome,
            local: this.itemEditado.local,
            capacidade: this.itemEditado.capacidade,
            descricao: this.itemEditado.descricao,
            ativo: this.itemEditado.ativo === "Ativado",
          })
          .then((res) => {
            //this.salas = res.data;
            alert("Os dados foram atualizados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });

        Object.assign(this.salas[this.editIndice], this.itemEditado);
      } else {
        axios
          .post(url,config, {
            nome: this.itemEditado.nome,
            local: this.itemEditado.local,
            capacidade: this.itemEditado.capacidade,
            descricao: this.itemEditado.descricao,
            ativo: true,
          })
          .then((res) => {
            this.salas = res.data;
            alert("Os dados foram adicionados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          });

        this.salas.push(this.itemEditado);
      }
      this.fechar();
    },
  },
};
</script>

