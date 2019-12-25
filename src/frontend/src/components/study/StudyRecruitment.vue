<template>
  <StudyCardsTemplateByStatus status="recruiting"/>
</template>

<script>
  `use strict`;

  import StudyCardsTemplateByStatus from "./StudyCardsTemplateByStatus";

  export default {
    name: `StudyRecruitment`,
    components: {StudyCardsTemplateByStatus},
  }
</script>
