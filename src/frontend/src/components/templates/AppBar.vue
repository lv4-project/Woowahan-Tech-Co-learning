<template>
  <v-app-bar
    :shrink-on-scroll="isInRecruitmentView"
    :prominent="isInRecruitmentView"
    :fade-img-on-scroll="isInRecruitmentView"
    absolute
    color="#6A76AB"
    dark
    src="@/assets/bg.jpg"
    scroll-target="#scrolling-techniques-3"
  >
    <template v-slot:img="{ props }">
      <v-img
        v-if="isInRecruitmentView"
        v-bind="props"
        gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
      />
    </template>

    <v-app-bar-nav-icon/>

    <v-toolbar-title>테코러닝</v-toolbar-title>

    <v-spacer/>

    <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-dots-vertical</v-icon>
    </v-btn>

    <template v-slot:extension>
      <v-tabs
        align-with-title
        background-color="transparent"
      >
        <v-tab>스터디</v-tab>
        <v-tab>책모임</v-tab>
        <v-tab>토이프로젝트</v-tab>
      </v-tabs>
    </template>
  </v-app-bar>
</template>

<script>
  export default {
    name: "AppBar",
    computed: {
      isInRecruitmentView() {
        return this.$route.name === `StudyRecruitment`;
      },
    },
  }
</script>