<template>
  <v-app id="inspire">
    <v-navigation-drawer v-model="drawer" app color="#f1f1f1">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6"> SIGL </v-list-item-title>
          <v-list-item-subtitle>
            Sistema Gerencial de Laboratório
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item v-for="item in items" :key="item.title" :to="item.to" link>
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar app color="#f1f1f1">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-app-bar-title
        ><img src="../assets/img/ittpac-logo.jpg" class="logo-itpac"
      /></v-app-bar-title>

      <v-spacer></v-spacer>
      <router-link :to="{ name: 'Login' }">
        <img src="../assets/img/exit-to-aplicativo.png" class="sair" />
      </router-link>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>

    <div class="footer">
      <h2 class="text-footer">
        SIGL - SISTEMA INTEGRADO DE GERENCIAMENTO DE LABORATÓRIOS
      </h2>
    </div>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    drawer: null,
    items: [
      { title: "PERÍODO", icon: "mdi-view-dashboard", to: "/periodo" },
      { title: "DISCIPLINA", icon: "mdi-book-multiple", to: "/disciplinas" },
      { title: "ALUNO", icon: "mdi-badge-account", to: "/academico" },
      { title: "PROFESSOR", icon: "mdi-account-tie", to: "/professor" },
      { title: "SUBGRUPO", icon: "mdi-account-group", to: "/subgrupo" },
      { title: "MANUAL", icon: "mdi-folder-cog", to: "/manuais" },
      { title: "SALA", icon: "mdi-google-classroom", to: "/salas" },
      { title: 'AGEND.-TECNICO', icon: 'mdi-notebook-edit', to:'/agendamento'},
      { title: 'AGEND.-ALUNO', icon: 'mdi-notebook-edit', to:'/agendamentoaluno'},
      { title: 'AGEND.-PROFESSOR', icon: 'mdi-notebook-edit', to:'/agendamentoprof'},
      { title: 'EVIDÊNCIAS', icon: 'mdi-page-next', to:'/evidencias'},
      { title: 'STATUS', icon: 'mdi-list-status', to:'/StatusAgendamento'},
      { title: 'DASHBOARD', icon: 'mdi-desktop-mac-dashboard', to:'/dashboard'},
      { title: 'USUÁRIO', icon: 'mdi-account-cog', to:'/usuario'},
      { title: 'SOBRE', icon: 'mdi-information', to:'/Sobre'},
      // { title: 'teste', icon: 'mdi-list-status', to:'/validate'},
      // { title: 'CALENDÁRIO', icon: 'mdi-calendar', to:'/calendario'}, 
      
    ],
  }),
};
</script>

<style scoped>
.footer {
  position: fixed;
  margin: 0;
  padding: 0;
  border: 0;
  left: 0;
  bottom: 0;
  width: 100%;
  background-color: #e11b22;
  color: white;
  text-align: center;
  font-family: Roboto, sans-serif;
  font-size: 9px;
}
.text-footer {
  padding: 12px;
}
.logo-itpac {
  padding-top: 2.2%;
  width: 17%;
  height: 17%;
}
.sair {
  width: 50px;
  height: 24px;
}
</style>































































    <!-- <v-img
          :src="require('../assets/logo.svg')"
          class="my-3"
          contain
          height="200"
        /> -->