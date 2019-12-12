<template>
  <v-bottom-navigation
    fixed
    grow
  >
    <v-btn
      @click="changeView(`recruitment`)"
      icon
    >
      <span>모집</span>
      <v-icon>mdi-account-group</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`ongoing`)"
      icon
    >
      <span>진행</span>
      <v-icon>mdi-airplane-takeoff</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`finished`)"
      icon
    >
      <span>완료</span>
      <v-icon>mdi-check</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`profile`)"
      icon
    >
      <span>프로필</span>
      <v-icon>mdi-account</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
  export default {
    name: "BottomNavigation",
    methods: {
      changeView(componentName) {
        this.$router.push(componentName)
      },
    },
  }
</script>

<style scoped>

</style>