<template>
  <v-data-table
    :headers="titulos"
    :items="manuais"
    :search="search"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Gerenciamento de Manuais</v-toolbar-title>
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
        <v-dialog v-model="dialog" max-width="350px">
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
              <span class="text-h6">Adicionar Manual</span>
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
                    <v-col cols="12" sm="4" md="8">
                      <v-text-field
                        v-model="itemEditado.descricao"
                        label="Descrição"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="4" md="8">
                      {{ itemEditado.fileName }}
                      <v-file-input
                        truncate-length="18"
                        v-model="itemEditado.fileName"
                        label="Arquivo"
                      ></v-file-input>
                    </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions id="card-actions">
              <v-btn small color="warning" dark @click="fechar">
                Cancelar
              </v-btn>
             <v-btn small color="primary" class="mr-4" @click="checkForm">Salvar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogEditar" max-width="350px">
          <v-card>
            
            <v-card-title>
              <span class="text-h6">Editar Descrição</span>
            </v-card-title>
            <p v-if="errors.length">
              <ul>
                <li v-for="error in errors2" :key="error">{{ error }}</li>
              </ul>
            </p>
            <v-card-text>
              <v-form>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="4" md="8">
                      <v-text-field
                        v-model="itemEditado.descricao"
                        label="Descrição"
                        
                      ></v-text-field>
                    </v-col>
                    <v-col cols="8" sm="6" md="4"> </v-col>
                    <v-col cols="8" sm="6" md="4"> </v-col>
                  </v-row>
                </v-container>
              </v-form>
            </v-card-text>
            <v-card-actions id="card-actions">
              <v-btn small color="warning" dark @click="dialogEditar = false">Cancelar</v-btn>
              <v-btn small color="primary" class="mr-4" @click="checkForm2">Salvar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDesativar" max-width="400px">
          <v-card class="card-modal">
            <v-card-title class="text-h6"
              >Deseja {{ mudarStatus }} este manual ?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="dialogDesativar = false"
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
      <v-icon small class="mr-2" @click="downloadArquivo(item)" color="brown"
        >mdi-download</v-icon
      >
      <v-icon small class="mr-2" @click="editDescricao(item)" color="blue"
        >mdi-pencil</v-icon
      >
      <v-icon small @click="desativeItem(item)" color="red">
        mdi-power-standby
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
</style>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

var url = "https://api-sig-itpac-84633.herokuapp.com/api/manual";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogEditar: false,
    dialogDesativar: false,
    titulos: [
      {
        text: "Descrição",
        value: "descricao",sortable: false
      },
      { text: "Arquivo", value: "fileName",sortable: false},
      { text: "Status", value: "ativo",sortable: false },
      { text: "Ações", align: "center", value: "acoes" },
    ],
    manuais: [],
    errors: [],
    errors2: [],
    editIndice: -1,
    filtros: ["Ativados", "Todos"],
    filtroSelecionado: "Ativados",
    itemEditado: {
      id: null,
      descricao: "",
      fileName: "",
      ativo: true,
    },
    itemPadrao: {
      id: null,
      descricao: "",
      fileName: "",
      ativo: null,
    },
  }),

  computed: {
    mudarStatus() {
      return this.itemEditado.ativo == "Ativado" ? "desativar " : "ativar";
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
    inicializar() {
      axios.get(url+"/getAll/false", this.manuais).then((res) => {
        this.manuais = res.data.map((p) => {
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
        axios.get(url + "/getAll/true", this.manuais).then((res) => {
          this.manuais = res.data.map((p) => {
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

    editDescricao(item) {
      this.editIndice = this.manuais.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogEditar = true;
    },

    desativeItem(item) {
      this.editIndice = this.manuais.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogDesativar = true;
    },

    downloadArquivo(item) {
      this.editIndice = this.manuais.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var index = this.itemEditado.id;
      axios
        .get(url+"/doc/" + index, {
          responseType: "blob",
        })
        .then((response) => {
          var headers = response.headers;
          var blob = new Blob([response.data], {
            type: headers["content-type"],
          });
          var link = document.createElement("a");
          link.href = window.URL.createObjectURL(blob);
          link.download = "file";
          link.click();
        })
        .catch((error) => {
          console.warn(error);
        });
    },

    desativeItemConfirm() {
      // this.manuais.splice(this.editIndice, 1);
      if (this.itemEditado.ativo == "Ativado") {
        axios
          .patch(url + "/desativar/" + this.itemEditado.id, {
            ativo: false,
          })
          .then((res) => {
            console.log(res.data);
            alert("O manual foi desativado com sucesso !");
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        axios
          .patch(url +"/ativar/"+ this.itemEditado.id, {
            ativo: true,
          })
          .then((res) => {
            console.log(res.data);
            alert("O manual foi ativado com sucesso !");
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

      checkForm() {
      if (this.itemEditado.descricao && this.itemEditado.fileName) {
        this.salvar();
        return true;
      }
      this.errors = [];

      if (!this.itemEditado.descricao) {
        this.errors.push("A descrição é obrigatória.");
      }
      if (!this.itemEditado.descricao) {
        this.errors.push("O Arquivo é obrigatório.");
      }
  },

  checkForm2() {
      if (this.itemEditado.descricao && this.itemEditado.fileName) {
        this.salvar();
        return true;
      }
      this.errors2 = [];

      if (!this.itemEditado.descricao) {
        this.errors2.push("A descrição é obrigatória.");
      }
      if (!this.itemEditado.descricao) {
        this.errors2.push("O Arquivo é obrigatório.");
      }
  },

    salvar() {
      const formData = new FormData();

      formData.set("descricao", this.itemEditado.descricao);

      if (this.editIndice > -1) {
        axios
          .patch(url + "/updateDescricao/" + this.itemEditado.id, formData)

          .then((res) => {
            this.itemEditado.descricao;
            console.log(res.data);
            alert("A descrição do manual foi alterada com sucesso !");
            this.reloadPage();
          });

        Object.assign(this.manuais[this.editIndice], this.itemEditado);
      } else {
        formData.append("file", this.itemEditado.fileName);

        axios
          .post(url, formData)

          .then((res) => {
            this.manuais = res.data;
            console.log(res.data);
            alert("O manual foi adicionado com sucesso !");
            this.reloadPage();
          });
        this.manuais.push(this.itemEditado);
      }

      this.fechar();
    },
  },
};
</script>
