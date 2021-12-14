<template>
  <div id="app">
    <Principal/> 
    <div class="footer">
      <h2 class="text-footer">
        SIGL - SISTEMA INTEGRADO DE GERENCIAMENTO DE LABORATÓRIOS
      </h2>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);
import Principal from "./components/Principal";
// const userKey = '__knowledge_user';
// import { userKey } from "@/global";
export default {
  name: "App",
  components: { Principal},
  data: () => ({
    return: {
      validatingToken: true,
    },
  }),
  methods: {
		async validateToken() {
      // const userKey = '__knowledge_user';
			// this.validatingToken = true

			// const json = localStorage.getItem(userKey)
			// const token = JSON.parse(json)
      // console.log(token.token, "hehe");
			// // this.$store.commit('setUser', null)
      // axios.defaults.headers.common['Authorization'] = `bearer ${token}`
      // if(userKey) {
         
      // } else {
      //     delete axios.defaults.headers.common['Authorization']
      // }

			// if(!userData) {
			// 	this.validatingToken = false
			// 	this.$router.push({ name: 'Login' })
			// 	return
			// }

			// const res = await axios.post(`${baseApiUrl}/signin`, userData)

		// 	if (res.data) {
		// 		this.$store.commit('setUser', userData)
		// 		this.$router.push({ name: 'dashboard' })
		// 		// if(this.$mq === 'xs' || this.$mq === 'sm') {
		// 		// 	this.$store.commit('toggleMenu', false)
		// 		// }
		// 	} else {
		// 		localStorage.removeItem(userKey)
		// 		this.$router.push({ name: 'Login' })
		// 	}
		// 	this.validatingToken = false
		}
	},
	mounted() {
		this.validateToken()
	}
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
</style>