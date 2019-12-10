<template>
  <v-app>
    <component :is="view" />
  </v-app>
</template>

<script>
  `user strict`;

  import Main from "./components/Main";
  import LoginForm from "./components/user/LoginForm";

  export default {
    name: `App`,
    components: {
      Main,
      LoginForm,
    },
    data() {
      return {
        view: `Main`,
      }
    }
  }
</script>

<style scoped>

</style>

