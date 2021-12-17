<template>
  <section class="section-form">
    <h1 class="text-h5 title-login">Login</h1>
    <v-card width="400" class="mx-auto mt-5 card-login p-1" id="login">
      <v-card-title class="pb-0"> </v-card-title>

      <v-card-text>
        <v-form>
          <v-text-field
            label="Usuário"
            v-model="user.username"
            prepend-icon="mdi-account-circle"
            color="red"
          />
          <v-text-field
            label="Senha"
            type="password"
            v-model="user.password"
            prepend-icon="mdi-lock"
            color="red"
          />
        </v-form>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-dialog v-model="recuperar" max-width="400">
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on"> Esqueceu a senha? </v-btn>
            <v-spacer></v-spacer>
          </template>

          <v-card width="400" class="mx-auto mt-5 card-login p-1">
            <v-card-title class="pb-0"> </v-card-title>
            <v-card-text>
              <v-form>
                <h5 class="text-h5">Recuperação de Senha</h5>
                <v-text-field
                  label="E-mail"
                  type="email"
                  v-model="email"
                  prepend-icon="mdi-account-circle"
                  color="red"
                />
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-btn id="btn-enviar" @click="enviarEmail">Enviar</v-btn>
            </v-card-actions>
          </v-card>
          
        </v-dialog>
        <v-btn center id="btn-logar" @click="signin">Login</v-btn>
      </v-card-actions>
    </v-card>

    <v-dialog v-model="dialogCodigo" max-width="400">
      <v-card width="400" height="300" class="mx-auto mt-5 card-login p-1">
        <v-card-title class="pb-0"> </v-card-title>
        <v-card-text>
          <v-form>
            <h5 class="text-h5">Confirmação do Código</h5>
            <v-text-field
              label="Código"
              type="codigo"
              v-model="codigo"
              prepend-icon="mdi-account-circle"
              color="red"
            />

            <v-text-field
              label="Nova Senha"
              type="password"
              v-model="novaSenha"
              prepend-icon="mdi-lock"
              color="red"
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <!-- <v-btn id="btn-enviar" @click = "enviarEmail" >Confirmar</v-btn> -->
          <v-btn id="btn-enviar" @click="novaSenha">Confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </section>
</template>

<style>
.section-form {
  padding: 5% 0;
  text-align: center;
  font-family: "Roboto", "Times New Roman", Times, serif;
}
#btn-logar {
  width: 50%;
  color: white;
  background-color: #e11b22;
  display: block !important;
  padding-inline: 1%;
}
.card-login {
  height: 250px;
}
.v-card-actions {
  align-items: center;
  text-align: center;
  padding-right: 2%;
}
#btn-enviar {
  width: 100%;
  color: white;
  background-color: #e11b22;
  display: block !important;
  padding-inline: 1%;
}
.v-card-actions {
  align-items: center;
  text-align: center;
  padding-right: 2%;
}
.text-h5 {
  padding-bottom: 5%;
  text-align: center;
  font-family: "Roboto", "Times New Roman", Times, serif;
}
</style>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);
import { baseApiUrl, userKey } from "@/global";
export default {
  name: "Login",
  data: function () {
    return {
      showSignup: false,
      email: "",
      codigo: "",
      senhaNova: "",
      dialogCodigo: false,
      dialogRecuperar: false,
      user: {},
    };
  },
  methods: {
    signin() {
      axios
        .post(`${baseApiUrl}/login/signin`, this.user)
        .then((res) => {
          this.$store.commit("setUser", res.data);
          localStorage.setItem(userKey, JSON.stringify(res.data));
          this.$router.push({ path: "/dashboard" });
        })
        .catch(console.log("algo errado"));
    },
    signup() {
      localStorage.setItem((userKey = "null"), JSON.stringify(null));
      this.$router.push({ path: "/" });
    },
    watch: {
      dialogRecuperar(val) {
        val || this.fechar();
      },
    },
    fechar() {
      this.dialogRecuperar = false;
    },
    recuperarSenha() {
      this.dialogRecuperar = true;
    },

    reloadPage(){
      window.Location.reload();
    },

    enviarEmail() {
      this.axios
        .get(
          "https://api-sig-itpac-84633.herokuapp.com/api/forgotpass?email=" +
            this.email
        )
        .then((res) => {
          alert("Email enviado com sucesso!");
          console.log(res.data);
        })
        .catch((error) => {
          console.log(error);
        });
      this.dialogCodigo = true;
    },

    novaSenha(){
      this.axios
        .get("https://api-sig-itpac-84633.herokuapp.com/api/forgotpass/alterpass?senha=" + this.senhaNova + "&codigo=" + this.codigo,{
          // codigo: this.codigo,
          // senhaNova: this.senhaNova,
        }
      )
      .then((res) => {
          alert("Senha alterada com sucesso!");
          console.log(res.data);
          this.reloadPage();
        })
        .catch((error) => {
          console.log(error);
        });
      this.dialogCodigo = false;
    }
  },
};
</script>