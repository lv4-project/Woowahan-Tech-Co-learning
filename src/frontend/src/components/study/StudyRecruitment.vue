<template>
  <div>
    <v-parallax
      height="300"
      src="../../assets/bg.jpg"
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

    <v-container>
      <v-row>
        <v-col align="center">
          <h2>모집중인 스터디</h2>
        </v-col>
      </v-row>

      <StudyCardsTemplateByStatus status="recruiting"/>
    </v-container>
  </div>
</template>

<script>
  `use strict`;

  import StudyCardsTemplateByStatus from "./StudyCardsTemplateByStatus";

  export default {
    name: `StudyRecruitment`,
    components: {StudyCardsTemplateByStatus},
  }
</script>
