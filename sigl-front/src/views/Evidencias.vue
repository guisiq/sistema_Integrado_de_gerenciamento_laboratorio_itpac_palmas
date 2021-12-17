<template>
  <v-data-table
    :headers="titulos"
    :items="evidencias"
    :search="search"
    :sortBy = "data"
    class="elevation-2 data-table"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Gerenciamento de Evidências</v-toolbar-title>
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
        <!-- editar/salvar -->
        <v-row justify="center">
          <v-dialog v-model="dialog" scrollable width="500px">
            <template v-slot:activator="{ on, attrs }" class="template-add">
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
                        <v-label>Agendamento</v-label>
                        <vue-select
                          v-model="agendamentoSelecionado"
                          :getOptionLabel="
                            (agendamentos) =>
                              agendamentos.motivo +
                              ' ( ' +
                              agendamentos.data +
                              ')'
                          "
                          :options="agendamentos"
                          label="Agendamento"
                          :rules="[(v) => !!v || '*Campo Obrigatório*']"
                          required
                        ></vue-select>
                      </v-col>
                      <v-col cols="8" sm="6" md="4">
                        <v-text-field
                          v-model="itemEditado.nomeAtividade"
                          label="Nome da Atividade"
                          :rules="[(v) => !!v || '*Campo Obrigatório*']"
                          required
                        ></v-text-field>
                      </v-col>
                      <v-col cols="8" sm="6" md="4">
                        <v-text-field
                          v-model="itemEditado.codigo"
                          label="Codigo"
                          :rules="[(v) => !!v || '*Campo Obrigatório*']"
                          required
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn small color="warning" dark @click="fechar"
                  >Cancelar</v-btn
                >
                <v-btn small color="primary" class="mr-4" @click="checkForm">Salvar</v-btn>
           
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
        <v-dialog v-model="dialogArquivo" max-width="500px" max-heigth="300px">
          <v-simple-table dense>
            <template v-slot:default>
              <thead>
                <tr>
                  <th>Evidência</th>
                  <th>Arquivos</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{{ itemEditado.nomeAtividade }}</td>
                  <td
                    class="td-lista"
                    v-for="(item, index) in files"
                    :key="index"
                  >
                    {{ item.fileName }}
                    <v-icon
                      color="purple"
                      class="icon"
                      @click="downloadArquivo(item)"
                    >
                      mdi-download</v-icon
                    >
                     <v-icon
                      color="green"
                      class="icon"
                      @click="deleteArquivo(item)"
                    >
                      mdi-delete</v-icon
                    >
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-dialog>
        <v-dialog v-model="dialogUpload" max-width="350px">
          <v-card>
            <v-card-title>
              <span class="text-h6">Enviar Arquivo</span>
            </v-card-title>

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
              <v-btn small color="warning" dark @click="dialogUpload = false">
                Cancelar
              </v-btn>
              <v-btn small color="primary" dark @click="enviarFile">
                Salvar
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogPresenca" max-width="350px">
          <v-simple-table dense>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="align-center">Presença</th>
                </tr>
              </thead>
                <v-row v-for="(item, index) in listapresenca"
                    :key="index">
                    <v-col cols="4" sm="4" md="4">
                        <v-checkbox
                          v-model="item.presente"   
                        ></v-checkbox>
                       
                    </v-col>
                    <v-col >
                      {{ item.nome }}
                    </v-col>
                </v-row>
               <v-spacer></v-spacer>
              <v-btn small right color="primary" dark @click="atualizarPresenca()" class="table-presenca"
                >Salvar</v-btn
              >
              <v-spacer></v-spacer>
            </template>
          </v-simple-table>
        </v-dialog>
        <v-dialog v-model="dialogRemover" max-width="400px">
          <v-card>
            <v-card-title class="text-h5"
              >Deseja excluir esta evidência?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small color="warning" dark @click="fecharDesativar">
                Não</v-btn
              >
              <v-btn small color="primary" dark @click="removeItemConfirm"
                >Sim</v-btn
              >marcarPresenca
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.acoes`]="{ item }">
      <v-icon small class="mr-2" @click="verArquivos(item)" color="purple"
        >mdi-folder</v-icon
      >
      <v-icon small class="mr-2" @click="marcarPresenca(item)" color="gray"
        >mdi-book</v-icon
      >
      <v-icon small class="mr-2" @click="uploadArquivo(item)" color="pink"
        >mdi-upload</v-icon
      >
      <v-icon small class="mr-2" @click="editItem(item)" color="blue">
        mdi-pencil
      </v-icon>
      <v-icon small @click="removeItem(item)" color="red"> mdi-delete </v-icon>
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
  padding: 2%;
  display: block;
}
.icon {
  margin: 1%;
}
.table-presenca{
  padding:10%;
}
</style>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

var url = "https://api-sig-itpac-84633.herokuapp.com/api/evidencia";
var urlAgendamento = "https://api-sig-itpac-84633.herokuapp.com/api/periodo/Agendamentos";

export default {
  data: () => ({
    data: null,
    agendamentoSelecionado:null,
    search: "",
    checkbox1:true,
    dialog: false,
    dialogUpload: false,
    dialogArquivo: false,
    dialogPresenca: false,
    dialogRemover: false,
    titulos: [
      { text: "Nome da Atividade", value: "nomeAtividade" },
      { text: "Sala", value: "sala" },
      { text: "Data", value: "data" },
      { text: "Subgrupo", value: "subgrupo", sortable: false },
      { text: "Codigo", value: "codigo", sortable: false },
      { text: "Ações", value: "acoes", sortable: false },
    ],
    listapresenca: [],
    errors: [],
    agendamentos: [],
    agendamentoRaw: [],
    evidencias: [],
    files: [],
    listaAlunos: [],
    descricao: "",
    editIndice: -1,
    itemEditado: {
      id: null,
      nomeAtividade: "",
      agendamentoSelecionado: null,
      codigo: null,
    },
    itemPadrao: {
      id: null,
      nomeAtividade: "",
      codigo: null,
    },
  }),

  computed: {
    tituloForm() {
      return this.editIndice === -1
        ? "Cadastrar Evidência"
        : "Editar Evidência";
    },
  },

  watch: {
    dialog(val) {
      val || this.fechar();
    },
    // dialogRemover(val) {
    //   val || this.fecharDesativar();
    // },
  },

  mounted() {
    this.inicializar();
  },

  methods: {
    async inicializar() {
      this.axios
        .get(url + "/info", this.evidencias)
        .then((res) => {
          this.evidencias = res.data;
        })
        .catch((error) => {
          console.warn(error);
        });
      await Promise.all([this.getAgendamentos()]);
    },

    reloadPage() {
      window.location.reload();
    },

    async getAgendamentos() {
      const { data } = await this.axios.get(urlAgendamento);
      this.agendamentoRaw = data;
      this.agendamentos = data;
      console.log(this.agendamentos, "agendamentos");
    },

    marcarPresenca(item) {
      this.dialogPresenca = true;
      //this.editIndice = this.listaAlunos.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var id = this.itemEditado.id;
      console.log(id, "id");
      // var dados = null;
      axios.get(url + "/presenca/" + id).then((res) => {
        this.listaAlunos = res.data;
        this.listapresenca = this.listaAlunos.listaAluno;
      });
    },

    atualizarPresenca() {
      
      const request = {
            id: this.itemEditado.id,
            listaAluno: this.listapresenca
      }
      axios.put(url + "/presenca",request).then((res) => {
 
       console.log(res.data, "lista de presença");
       alert("A lista foi atualizada com sucesso !");
       this.reloadPage();
      }).catch((p)=>{
        console.log(p)
      });

      this.dialogPresenca = false;
    },

    uploadArquivo(item) {
      this.editIndice = this.evidencias.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogUpload = true;
    },

    verArquivos(item) {
      this.editIndice = this.evidencias.indexOf(item);
      this.itemEditado = Object.assign({}, item);

      var id = this.itemEditado.id;
      console.log(id, "id");
      // var dados = null;
      axios.get(url + "/arquivo?idEvidencia=" + id).then((res) => {
        this.files = res.data;
        console.log(this.files, "oie");
      });

      this.dialogArquivo = true;
    },

    downloadArquivo(item) {
      this.editIndice = this.files.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var idx = this.itemEditado.id;

      axios
        .get(url + "/arquivo/doc/" + idx, {
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
      console.log(idx, "id file");
    },

    enviarFile() {
      var idx = this.itemEditado.id;
      const formData = new FormData();
      formData.set("descricao", this.itemEditado.descricao);
      formData.append("file", this.itemEditado.fileName);

      axios.post(url + "/arquivo/" + idx, formData).then((res) => {
        this.evidencias = res.data;
        console.log(res.data);
        alert("O arquivo foi enviado com sucesso !");
        this.reloadPage();
      });
    },

     deleteArquivo(item) {
      this.editIndice = this.files.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      var idx = this.itemEditado.id;
      console.log(idx,"arquivoo");

      const formData = new FormData();
      formData.append("file", this.itemEditado.fileName);

      axios.delete(url + "/arquivo/" + idx, formData).then((res) => {
        console.log(res.data,"arquivo removido");
        alert("O arquivo foi excluído com sucesso !");
        this.reloadPage();
      });
    },

    editItem(item) {
      this.editIndice = this.evidencias.indexOf(item);
      this.itemEditado = Object.assign({}, item);

      this.dialog = true;
    },

    removeItem(item) {
      this.editIndice = this.evidencias.indexOf(item);
      this.itemEditado = Object.assign({}, item);
      this.dialogRemover = true;
    },

    removeItemConfirm() {
      axios
        .delete(url + "/info/" + this.itemEditado.id, {
          id: this.itemEditado.id,
        })
        .then((res) => {
          console.log(res.data);
          alert("Esta evidência foi excluida com sucesso !");
          this.reloadPage();
        })
        .catch((error) => {
          console.log(error);
        });
    },

    fechar() {
      this.dialog = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
      });
    },

    fecharDesativar() {
      this.dialogRemover = false;
      this.$nextTick(() => {
        this.itemEditado = Object.assign({}, this.itemPadrao);
        this.editIndice = -1;
      });
    },
      checkForm() {
      if (this.itemEditado.agendamentoSelecionado 
      && this.itemEditado.nomeAtividade 
      && this.itemEditado.codigo) {
        this.salvar();
        return true;
      }
      this.errors = [];

      if (!this.itemEditado.nomeAtividade) {
        this.errors.push("O nome da atividade é obrigatório.");
      }
      if (!this.itemEditado.codigo) {
        this.errors.push("O codigo é obrigatório.");
      }
      if (!this.itemEditado.agendamentoSelecionado) {
        this.errors.push("O agendamento é obrigatório.");
      }
    },

    salvar() {
      if (this.editIndice > -1) {
        axios
          .put(url + "/info/" , {
            id: this.itemEditado.id,
            nomeAtividade: this.itemEditado.nomeAtividade,
            idagendamento: this.agendamentoSelecionado.id,
            codigo: this.itemEditado.codigo,
          })
          .then((res) => {
            alert("Os dados foram atualizados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          })
          .catch((error) => {
            console.log(error);
          });

        Object.assign(this.evidencias[this.editIndice], this.itemEditado);
      } else {
        axios
          .post(url + "/info/", {
            id: this.itemEditado.id,
            nomeAtividade: this.itemEditado.nomeAtividade,
            idagendamento: this.agendamentoSelecionado.id,
            codigo: this.itemEditado.codigo,
          })
          .then((res) => {
            this.evidencias = res.data;
            alert("Os dados foram adicionados com sucesso !");
            console.log(res.data);
            this.reloadPage();
          });

        this.evidencias.push(this.itemEditado);
      }
      this.fechar();
    },
  },
};
</script>

