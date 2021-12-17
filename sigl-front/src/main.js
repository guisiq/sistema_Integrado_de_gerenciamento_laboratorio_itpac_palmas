import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import VueMask from 'v-mask'
import VueI18n from 'vue-i18n'
import router from './router'
import vSelect from "vue-select"
import "vue-select/dist/vue-select.css"
import store from './store'
Vue.component("vue-select", vSelect);
Vue.use(VueMask);
Vue.use(VueI18n);
Vue.config.productionTip = false

// const jwt = require("jsonwebtoken");

export default new VueI18n({
  silentTranslationWarn: true,
  locale: 'pt',
  fallbackLocale: 'dev',
  messages: {
    dev: { hello_world: 'Hello World!' }
  }
})

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
