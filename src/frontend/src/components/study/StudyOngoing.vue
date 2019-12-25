<template>
  <StudyCardsTemplateByStatus status="ongoing"/>
</template>

<script>
  `use strict`;

  import StudyCardsTemplateByStatus from "./StudyCardsTemplateByStatus";

  export default {
    name: `StudyOngoing`,
    components: {StudyCardsTemplateByStatus},
  };
</script>
