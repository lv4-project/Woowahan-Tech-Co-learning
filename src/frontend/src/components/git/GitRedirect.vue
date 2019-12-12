<template>
  <v-app>
  </v-app>
</template>

<script>
  export default {
    name: "GitRedirect",
    loginData: {
      code: ``,
    },
    methods: {
      login() {
        const vue = this;
        const request = require('request');
        request.get({
          url: `${window.location.origin}/api/oauth?code=${vue.$route.query.code}`,
          json: true,
        }, function (error, response, body) {
          if ((response && response.statusCode) === 200) {
            vue.$router.push(`/`);
          } else {
            window.alert(body);
            vue.$router.push(`/gitsignup`);
          }
        });
      },
    },
    mounted() {
      this.login();
    }
  }
</script>