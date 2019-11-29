<template>
  <v-app>
    <StudyRecruitment/>
    <OngoingStudy/>
    <FinishedStudy/>
    <MyPage/>
    <ParticipatingStudy/>
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
      //
    }),
  };
</script>

