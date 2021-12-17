<template>
  <v-data-table
    :headers="titulos"
    :items="periodos"
    :search="search"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Gerenciamento de Período</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <!-- <v-ster></v-ster>  -->
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
        <v-dialog v-model="dialog" max-width="400px">
          <template v-slot:activator="{ on, attrs }">
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
                        v-model="itemEditado.periodo"
                        label="Periodo"
                        v-mask="'####/##'"
                      ></v-text-field>
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
              small color="primary" 
              class="mr-4"
              @click="checkForm">Salvar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDesativar" max-width="450px">
          <v-card>
            <v-card-title class="text-h5"
              >Deseja {{mudarStatus}} este período?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color=" warning" dark @click="fecharDesativar"
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
      <v-icon small class="mr-2" @click="editItem(item)" color="blue"> mdi-pencil </v-icon>
      <v-icon small @click="desativeItem(item)" color="red"> mdi-power-standby </v-icon>
    </template>
  </v-data-table>
</template>

<style>
.add {
  width: 5%;
  height: 5%;
}
body {
  padding: 2%;
}

.data-table {
  padding: 3%;
}
</style>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import { baseApiUrl, userKey } from "@/global";
Vue.use(VueAxios, axios);

// Patch /desativar/"; Dispatch /ativar/";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDesativar: false,
    titulos: [
      {
        text: "Periodo",
        value: "periodo",
        sortable: false,
      },
      {
        text: "Data Cadastro",
        value: "dataCadastro",
        sortable: false,
      },
      {
        text: "Status",
        value: "ativo",
        sortable: false,
      },
      { text: "Ações", value: "acoes", sortable: false },
    ],
    errors: [],
    periodos: [],
    editIndice: -1,
    filtros: ["Ativados", "Todos"],
    filtroSelecionado: "Ativados",
    itemEditado: {
      id: null,
      periodo: "",
      dataCadastro: "",
      ativo: true,
    },
    itemPadrao: {
      id: null,
      periodo: "",
      dataCadastro: "",
      ativo: true,
    },
  }),

  computed: {
    tituloForm() {
      return this.editIndice === -1 ? "Cadastrar Periodo" : "Editar Dados";
    },
    mudarStatus() {
      return this.itemEditado.ativo == "Ativado" ? "desativar " : "ativar";
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
      axios.defaults.headers.common['Authorization'] = `Bearer ${jwt.token}`
     

      axios.get(`${baseApiUrl}/api/periodo/getAll/false`, this.periodos).then((res) => {
        this.periodos = res.data.map((p) => {
          p.ativo = p.ativo ? "Ativado" : "Desativado";
          return p;
        });
        console.log(res.data);
      }).catch(console.warn("erro"));
    },

    redefinirTable() {
      if (this.filtroSelecionado === "Todos") {
        this.inicializar();
      } else {
        axios.get(`${baseApiUrl}/api/periodo/getAll/false`, this.periodos).then((res) => {
          this.periodos = res.data.map((p) => {
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
      this.editIndice = this.periodos.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialog = true;
    },

    checkForm() {
      if (this.itemEditado.periodo) {
        this.salvar();
        return true;
      }
      this.errors = [];

      if (!this.itemEditado.periodo) {
        this.errors.push("O período é obrigatório.");
      }
    },

    desativeItem(item) {
      this.editIndice = this.periodos.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogDesativar = true;
    },

    desativeItemConfirm() {
      const json = localStorage.getItem(userKey);
      const jwt = JSON.parse(json);
      console.log(jwt.token);

       var config = {
        headers: { "Authorization": ' bearer '+ jwt.token },
      };

      if (this.itemEditado.ativo == "Ativado") {
        axios
          .patch(`${baseApiUrl}/api/periodo/desativar` + this.itemEditado.id,config, {
            id: this.itemEditado.id,
            // dataCadastro: this.itemEditado.dataCadastro,
            ativo: false,
          })
          .then((res) => {
            alert("O período foi desativado com sucesso !");
            console.log(res.data);
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        axios
          .patch(`${baseApiUrl}/api/periodo/ativar` + this.itemEditado.id,config, {
            ativo: true,
          })
          .then((res) => {
            console.log(res.data);
            alert("O período foi ativado com sucesso !");
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

    salvar() {
      const json = localStorage.getItem(userKey);
      const jwt = JSON.parse(json);
      console.log(jwt.token);

       var config = {
        headers: { "Authorization": ' bearer '+ jwt.token },
      };

      if (this.editIndice > -1) {
        axios
          .put(`${baseApiUrl}/api/periodo`,config, {
            id: this.itemEditado.id,
            periodo: this.itemEditado.periodo,
            ativo: this.itemEditado.ativo === "Ativado",
          })
          .then((res) => {
            //this.periodos = res.data
            alert("Os dados foram atualizados com sucesso !");
            console.log(res.data);
            console.log(jwt.token);
          });
        Object.assign(this.periodos[this.editIndice], this.itemEditado);
      } else {
        axios
          .post(`${baseApiUrl}/api/periodo`,config, {
            periodo: this.itemEditado.periodo,
            ativo: true,
          })
          .then((res) => {
            this.periodos = res.data;
            alert("Os dados foram adicionados com sucesso !");
            console.log(res.data);
            console.log(jwt.token);
            // this.reloadPage();
          });
        this.periodos.push(this.itemEditado);
      }

      this.fechar();
    },
  },
};
</script>
