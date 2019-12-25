<template>
  <v-app>
    <v-content>
      <v-app-bar
        v-scroll
        fixed
      >
        <v-spacer/>
        <v-toolbar-title>테코러닝</v-toolbar-title>
        <v-spacer/>

        <v-toolbar-items>
          <MainMenu/>
        </v-toolbar-items>
      </v-app-bar>

      <div style="height: 50px;"></div>

      <v-parallax
        height="300"
        src="../assets/bg.jpg"
        dark
      >
        <v-row
          align="center"
          justify="center"
        >
          <v-col class="text-center" cols="12">
            <h1>Tecolearning</h1>
            <h4 class="subheading">Build your STUDY today!</h4>
          </v-col>
        </v-row>
      </v-parallax>

      <v-sheet>
        <v-container>
          <router-view/>
        </v-container>
      </v-sheet>
    </v-content>

    <div style="height: 50px;"></div>

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

