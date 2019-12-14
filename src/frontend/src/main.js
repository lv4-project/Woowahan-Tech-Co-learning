import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import request from 'request'

Vue.config.productionTip = false;
Vue.prototype.$request = request;

new Vue({
  router,
  vuetify,
  render: h => h(App),
}).$mount('#app');
