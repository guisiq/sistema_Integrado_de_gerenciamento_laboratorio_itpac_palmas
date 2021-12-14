<template>
  <v-data-table
    :headers="titulos"
    :items="usuario"
    :search="search"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Gerenciamento de Usuário</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-ster></v-ster>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Pesquisar"
          single-line
          hide-details
        ></v-text-field>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="400px">
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
                      
 <!-- Tem que alterar os Atributos de acordo com o contexto -->

                      <v-label>Perfil</v-label>
                      <vue-select
                        :getOptionLabel="(professor) => professor.pessoa.nome"
                        v-model="profSelecionado"
                        :options="professor"
                        :rules="['*Campo Obrigatorio*']"
                        :search="search"
                        label="professor"
                      ></vue-select>
                   
                      <v-text-field
                        v-model="itemEditado.login"
                        label="Login"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="8" sm="6" md="4">
                      <v-text-field
                        v-model="itemEditado.senha"
                        label="Senha"
                        :rules="['*Campo Obrigatorio*']"
                      ></v-text-field>

<!-- Tem que alterar os Atributos de acordo com o contexto -->
                      
                      <v-label>Pessoa</v-label>
                      <vue-select
                        :getOptionLabel="(professor) => professor.pessoa.nome"
                        v-model="profSelecionado"
                        :options="professor"
                        :rules="['*Campo Obrigatorio*']"
                        :search="search"
                        label="professor"
                      ></vue-select>
                    </v-col>
            
                    <v-col cols="8" sm="6" md="4"> </v-col>
                    <v-col cols="8" sm="6" md="4"> </v-col>
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
                  <th class="text-center">Perfil</th>
                  <th class="text-center">Pessoa</th>
                  <th class="text-center">Senha</th>
                  <th class="text-center">Login</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{{ itemEditado.perfil }}</td>
                  <td>{{ itemEditado.pessoa }}</td>
                  <td>{{ itemEditado.senha }}</td>
                  <td>{{ itemEditado.login }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-dialog>

        <v-dialog v-model="dialogDesativar" max-width="400px">
          <v-card class="card-modal">
            <v-card-title class="text-h6"
              >Deseja {{ mudarStatus }} este professor ?</v-card-title
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
</style>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

var url = "http://api-sig-itpac-84633.herokuapp.com/api/usuario";
var urlPatch =
  "http://api-sig-itpac-84633.herokuapp.com/api/usuario/desativar/";
var urlDispatch =
  "http://api-sig-itpac-84633.herokuapp.com/api/usuario/ativar/";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDesativar: false,
    dialogDetalhar: false,
    titulos: [
      { text: "Perfil", value: "perfil" },
      { text: "Pessoa", value: "pessoa" },
      { text: "Senha", value: "senha" },
      { text: "Login", value: "login" },
      { text: "Ações", value: "acoes" },
    ],

    usuario: [],
    editIndice: -1,
    itemEditado: {
      id: null,
      perfil: "",
      pessoa: "",
      senha: "",
      login: "",
      ativo: true,
    },
    itemPadrao: {
      id: null,
        perfil: "",
      pessoa: "",
      senha: "",
      login: "",
      ativo: true,
    },
    esta_ativo: true,
  }),
  computed: {
    tituloForm() {
      return this.editIndice === -1 ? "Cadastrar Usuário" : "Editar Usuário";
    },
    mudarStatus() {
      return this.itemEditado.ativo === "Ativado" ? " desativar " : " ativar ";
    },
  },
  props: {
    pessoa: {
      type: Object,
      default: function () {
        return {
          perfil: "",
          pessoa: "",
          senha: "",
          login: "",
        };
      },
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
    inicializar() {
      axios.get(url+"/getAll/false", this.usuario).then((res) => {
        this.usuario = res.data.map((p) => {
          p.ativo = p.ativo ? "Ativado" : "Desativado";
          return p;
        });

        console.log("res.data");
        console.table(res.data);
        console.log("this.usuario");
        console.log(this.usuario);
      });
    },

    reloadPage() {
      window.location.reload();
    },

    editItem(item) {
      this.editIndice = this.usuario.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialog = true;
    },

    desativeItem(item) {
      this.editIndice = this.usuario.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogDesativar = true;
    },

    desativeItemConfirm() {
      if (this.itemEditado.ativo === "Ativado") {
        axios
          .patch(urlPatch + this.itemEditado.id, {
            ativo: false,
          })
          .then((res) => {
            console.log(res.data);
            alert("O usuário foi desativado com sucesso !");
            this.reloadPage();
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
            alert("O usuário foi ativado com sucesso !");
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
      this.editIndice = this.usuario.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var id = this.itemEditado.id;
      axios.get(url + "/" + id).then((res) => {
        this.itemEditado = res.data;
      });

      this.dialogDetalhar = true;
    },

    salvar() {
      if (this.editIndice > -1) {
        axios
          .put(url, {
            id: this.itemEditado.id,
            perfil: this.itemEditado.perfil,
            pessoa: this.itemEditado.pessoa,
            senha:  this.itemEditado.senha,
            login:  this.itemEditado.login,
            ativo:  this.itemEditado.ativo === "Ativado",
          })
          .then((res) => {
            alert("Os dados foram atualizados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });

        Object.assign(this.usuario[this.editIndice], this.itemEditado);
      } else {
        axios
          .post(url, {
            perfil: this.itemEditado.perfil,
            pessoa: this.itemEditado.pessoa,
            senha:  this.itemEditado.senha,
            login:  this.itemEditado.login,
            ativo: true,
          })
          .then((res) => {
            this.usuario = res.data;
            console.log(res.data);
            alert("Os dados foram adicionados com sucesso !");
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });

        this.usuario.push(this.itemEditado);
      }

      this.fechar();
    },
  },
};
</script>