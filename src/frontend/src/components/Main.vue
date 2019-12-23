<template>
  <v-app>
    <v-card class="overflow-hidden">
      <v-app-bar
        :shrink-on-scroll="isInRecruitmentView"
        :prominent="isInRecruitmentView"
        :fade-img-on-scroll="isInRecruitmentView"
        absolute
        color="#6A76AB"
        dark
        src="../assets/bg.jpg"
        scroll-target="#scrolling-techniques-3"
      >
        <template v-slot:img="{ props }">
          <v-img
            v-if="isInRecruitmentView"
            v-bind="props"
            gradient="to top right, rgba(99,114,200,.4), rgba(25,32,72,.7)"
          />
        </template>

        <MainMenu/>

        <v-toolbar-title>테코러닝</v-toolbar-title>

        <v-spacer/>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn
          @click="routeToStudyGeneration"
          icon
        >
          <v-icon>mdi-square-edit-outline</v-icon>
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
      <v-sheet
        id="scrolling-techniques-3"
        class="overflow-y-auto"
        :max-height="getDeviceHeight"
        fixed
      >
        <v-container :style="getMainSheetStyle">
          <router-view></router-view>
        </v-container>
      </v-sheet>
    </v-card>

    <BottomNavigation/>
  </v-app>
</template>

<script>
  `use strict`;

  import BottomNavigation from "./templates/BottomNavigation";
  import MainMenu from "./login/LoginBtn";

  export default {
    name: `Main`,
    components: {
      BottomNavigation,
      MainMenu,
    },
    data() {
      return {
        sheetMarginTop: `0px`,
        sheetMarginBottom: `0px`,
      }
    },
    methods: {
      resetSheetMargin() {
        this.sheetMarginTop = document.getElementsByTagName(`header`)[0].style.height;
        this.sheetMarginBottom = document.getElementsByClassName(`v-bottom-navigation`)[0].style.height
      },
      routeToStudyGeneration() {
        this.$router.push(`/generation`)
      },
      showLogin() {
        this.$router.push(`/login`)
      }
    },
    computed: {
      isInRecruitmentView() {
        return this.$route.name === `StudyRecruitment`;
      },
      getMainSheetStyle() {
        const marginTop = parseInt(this.sheetMarginTop);
        const marginBottom = parseInt(this.sheetMarginBottom);
        const minHeight = window.screen.height - marginTop - marginBottom;

        return `min-height: ${String(minHeight)}px;
                margin-top: ${String(marginTop)}px;
                margin-bottom: ${String(marginBottom)}px;`
      },
      getDeviceHeight() {
        return window.screen.height;
      },
    },
    mounted() {
      this.$router.push(`recruitment`);
      this.resetSheetMargin();
    },
    updated() {
      this.resetSheetMargin();
    },
  };
</script>

