import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: `#4F5EB4`,
        secondary: `#703E83`,
      },
    },
  },
});
