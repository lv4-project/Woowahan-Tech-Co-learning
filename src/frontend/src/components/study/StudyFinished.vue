<template>
  <v-container>
    <v-row>
      <v-col align="center">
        <h2>Finished Study</h2>
      </v-col>
    </v-row>

    <StudyCardsTemplateByStatus status="finished"/>
  </v-container>
</template>

<script>
  import StudyCardsTemplateByStatus from "./StudyCardsTemplateByStatus"

  export default {
    name: `StudyFinished`,
    components: {StudyCardsTemplateByStatus},
    data: () => ({}),
  };
</script>
