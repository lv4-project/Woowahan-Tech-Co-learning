<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script>
  `user strict`;

  export default {
    name: `App`,
    data() {
      return {
        view: `Main`,
      }
    },
  }
</script>

<style scoped>

</style>

