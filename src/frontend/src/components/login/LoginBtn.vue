<template>
  <v-menu>
    <template v-slot:activator="{ on }">
      <v-btn
        icon
        color="yellow"
        v-on="on"
      >
        <v-app-bar-nav-icon/>
      </v-btn>
    </template>

    <v-list>
      <v-list-item
        v-show="loggedInUser == null"
      >
        <v-list-item-title @click="showLogin">로그인</v-list-item-title>
      </v-list-item>
      <v-list-item
        v-show="loggedInUser != null"
      >
        <v-list-item-title @click="logout">로그아웃</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script>
  export default {
    name: `MainMenu`,
    data() {
      return {
        loggedInUser: null,
      }
    },
    methods: {
      showLogin() {
        this.$router.push(`/login`)
      },
      logout() {
        this.$request.get(
          {
            url: `${window.location.origin}/api/oauth/logout`,
          },
          (error, response) => {
            if ((response && response.statusCode) === 200) {
              this.loggedInUser = null;
            }
            // TODO: error
          });
      },
    },
    mounted() {
      this.$request.get(
        {
          url: `${window.location.origin}/api/users/myinfo`,
        },
        (error, response, body) => {
          if ((response && response.statusCode) === 200) {
            this.loggedInUser = JSON.parse(body);
          } else {
            this.loggedInUser = null;
          }
        });
    }
  }
</script>

<style scoped>

</style>