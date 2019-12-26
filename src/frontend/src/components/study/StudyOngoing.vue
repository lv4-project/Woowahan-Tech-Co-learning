<template>
  <v-container>
    <v-row>
      <v-col align="center">
        <h2>Ongoing Study</h2>
      </v-col>
    </v-row>

    <StudyCardsTemplateByStatus status="ongoing"/>
  </v-container>
</template>

<script>
  `use strict`;

  import StudyCardsTemplateByStatus from "./StudyCardsTemplateByStatus";

  export default {
    name: `StudyOngoing`,
    components: {StudyCardsTemplateByStatus},
  };
</script>
