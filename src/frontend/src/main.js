import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import VueJWT from 'vuejs-jwt'
import VueCookies from 'vue-cookies'

Vue.use(VueCookies)
Vue.use(VueJWT);

Vue.config.productionTip = false;

new Vue({
  router,
  vuetify,
  render: h => h(App),
}).$mount('#app');
