import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import request from 'request'
import './registerServiceWorker'

Vue.config.productionTip = false;
Vue.prototype.$request = request;
export const eventBus = new Vue();

new Vue({
  router,
  vuetify,
  render: h => h(App),
}).$mount('#app');
