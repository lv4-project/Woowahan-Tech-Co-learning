<template>
  <v-app>
    <v-row>
      <v-toolbar dense>
        <v-app-bar-nav-icon></v-app-bar-nav-icon>

        <v-toolbar-title>Title</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </v-toolbar>
    </v-row>
    <v-row>
      <component :is="view"></component>
    </v-row>
    <v-row>
      <v-col>
        <v-btn @click="view = 'StudyRecruitment'">모집</v-btn>
      </v-col>
      <v-col>
        <v-btn @click="view = 'OngoingStudy'">진행중</v-btn>
      </v-col>
      <v-col>
        <v-btn @click="view = 'FinishedStudy'">완료</v-btn>
      </v-col>
      <v-col>
        <v-btn @click="view = 'MyPage'">MyPage</v-btn>
      </v-col>
      <v-col>
        <v-btn @click="view = 'ParticipatingStudy'">MyStudy</v-btn>
      </v-col>
    </v-row>
  </v-app>
</template>

<script>
  import StudyRecruitment from './components/study/StudyRecruitment';
  import OngoingStudy from "./components/study/OngoingStudy";
  import FinishedStudy from "./components/study/FinishedStudy";
  import MyPage from "./components/user/MyPage";
  import ParticipatingStudy from "./components/user/ParticipatingStudy";

  export default {
    name: 'App',
    components: {
      StudyRecruitment,
      OngoingStudy,
      FinishedStudy,
      MyPage,
      ParticipatingStudy,
    },
    data: () => ({
      view: 'StudyRecruitment'
    }),
  };
</script>

