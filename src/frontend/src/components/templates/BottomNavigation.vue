<template>
  <v-bottom-navigation
    fixed
    grow
  >
    <v-btn
      @click="changeView(`StudyRecruitment`)"
      icon
    >
      <span>모집중</span>
      <v-icon>mdi-account-group</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`StudyOngoing`)"
      icon
    >
      <span>진행중</span>
      <v-icon>mdi-airplane-takeoff</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`StudyFinished`)"
      icon
    >
      <span>완료됨</span>
      <v-icon>mdi-check</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`UserProfile`)"
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
        this.$emit(`updatedView`, componentName);
        window.console.log(componentName);
      },
    },
  }
</script>

<style scoped>

</style>