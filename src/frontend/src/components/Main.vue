<template>
  <v-app>
    <v-app-bar
      fixed
      inverted-scroll
    >
      <v-spacer/>
      <v-toolbar-title>테코러닝</v-toolbar-title>
      <v-spacer/>

      <v-toolbar-items>
        <MainMenu/>
      </v-toolbar-items>
    </v-app-bar>

    <router-view/>

    <div style="height: 55px;"></div>

    <BottomNavigation/>
  </v-app>
</template>

<script>
  `use strict`;

  import BottomNavigation from "./templates/BottomNavigation";

  export default {
    name: `Main`,
    components: {
      BottomNavigation,
    },
    data() {
      return {}
    },
    methods: {
      showLogin() {
        this.$router.push(`/login`)
      }
    },
    computed: {
      isInRecruitmentView() {
        return this.$route.name === `StudyRecruitment`;
      },
    },
    mounted() {
      this.$router.push(`recruitment`);
    },
  };
</script>

