<template>
  <section class="section-form" id="recuperacao">
    <h1 class="text-h5">Recuperação de Senha</h1>
    <v-card width="400" class="mx-auto mt-5 card-login p-1">
      <v-card-title class="pb-0"> </v-card-title>
      <v-card-text>
        <v-form>
          <v-text-field label="Nova Senha" prepend-icon="mdi-lock" />
          <v-text-field label="Confirmar Senha" prepend-icon="mdi-lock" />
        </v-form>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn id="btn-salvar">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </section>
</template>

<style>
.section-form {
  padding: 5% 0;
  text-align: center;
  font-family: "Roboto", "Times New Roman", Times, serif;
}
#btn-salvar {
  width: 100%;
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
</style>

<script>
export default {
  name: "Recuperacao",
};
</script>
