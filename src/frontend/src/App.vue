<template>
  <v-app>
    <v-row>
      <MainToolbar />
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
  import MainToolbar from "./MainToolbar";
  import StudyGeneration from "./components/study/StudyGeneration";

  export default {
    name: 'App',
    components: {
      StudyGeneration,
      StudyRecruitment,
      OngoingStudy,
      FinishedStudy,
      MyPage,
      ParticipatingStudy,
      MainToolbar,
    },
    data: () => ({
      view: 'StudyRecruitment'
    }),
  };
</script>

