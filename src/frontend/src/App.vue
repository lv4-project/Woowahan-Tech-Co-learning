<template>
  <v-app>
    <v-card class="overflow-hidden">
      <v-app-bar
        :shrink-on-scroll="isInMainView"
        :prominent="isInMainView"
        :fade-img-on-scroll="isInMainView"
        absolute
        color="#6A76AB"
        dark
        src="./assets/bg.jpg"
        scroll-target="#scrolling-techniques-3"
      >
        <template v-slot:img="{ props }">
          <v-img
            v-if="isInMainView"
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
      <v-sheet
        id="scrolling-techniques-3"
        class="overflow-y-auto"
        max-height="600"
      >
        <v-container :style="getMainSheetStyle">
          <v-row>
            <component :is="view" v-on:updatedView="updateView"/>
          </v-row>
        </v-container>
      </v-sheet>
    </v-card>

    <BottomNavigation v-on:updatedView="updateView"/>
  </v-app>
</template>

<script>
  `use strict`;

  import StudyRecruitment from "./components/study/StudyRecruitment";
  import StudyOngoing from "./components/study/StudyOngoing";
  import StudyFinished from "./components/study/StudyFinished";
  import UserProfile from "./components/user/UserProfile";
  import ParticipatingStudy from "./components/user/ParticipatingStudy";
  import UserUpdateForm from "./components/user/UserUpdateForm";
  import BottomNavigation from "./components/templates/BottomNavigation";

  export default {
    name: `App`,
    components: {
      StudyRecruitment,
      StudyOngoing,
      StudyFinished,
      UserProfile,
      ParticipatingStudy,
      UserUpdateForm,
      BottomNavigation,
    },
    data() {
      return {
        view: `StudyRecruitment`,
        sheetMarginTop: `0px`,
        sheetMarginBottom: `0px`,
        loginId: 1,
      }
    },
    methods: {
      resetSheetMargin() {
        this.sheetMarginTop = document.getElementsByTagName(`header`)[0].style.height;
        this.sheetMarginBottom = document.getElementsByClassName(`v-bottom-navigation`)[0].style.height
      },
      updateView(componentName) {
        this.view = componentName;
      }
    },
    computed: {
      isInMainView() {
        return this.view === `StudyRecruitment`;
      },
      getMainSheetStyle() {
        const marginTop = parseInt(this.sheetMarginTop);
        const marginBottom = parseInt(this.sheetMarginBottom);
        const minHeight = window.screen.height - marginTop - marginBottom;

        return `min-height: ${String(minHeight)}px;
                margin-top: ${String(marginTop)}px;
                margin-bottom: ${String(marginBottom)}px;`
      },
    },
    mounted() {
      this.resetSheetMargin();
    },
    updated() {
      this.resetSheetMargin();
    },
  };
</script>

