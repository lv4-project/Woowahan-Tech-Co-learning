<template>
  <StudyCardsTemplateByStatus status="finished"/>
</template>

<script>
  import StudyCardsTemplateByStatus from "./StudyCardsTemplateByStatus"

  export default {
    name: `StudyFinished`,
    components: {StudyCardsTemplateByStatus},
    data: () => ({}),
  };
</script>
