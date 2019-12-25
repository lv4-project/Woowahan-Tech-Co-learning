<template>
  <div>
    <StudyCard
      v-for="study in studies"
      :key="study.id"
      :study="study"
    />
    <div id="bottomSensor"></div>
  </div>
</template>

<script>
  `use strict`;

  import StudyCard from "./StudyCard";

  export default {
    name: `StudyCardsTemplateByStatus`,
    components: {StudyCard},
    props: {
      status: String,
    },
    data() {
      return {
        studies: [],
        pageOffset: 0,
        pageSize: 5,
      }
    },
    methods: {
      requestStudySummaryPage(pageOffset, pageSize) {
        const request = `${window.location.origin}/api/studies/summary?` +
          `studyStatus=${this.$props.status}&page=${String(pageOffset)}&size=${String(pageSize)}&sort=createdDate,desc`;

        this.$request.get(request,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.studies = this.studies.concat(JSON.parse(body));
              this.pageOffset++;
            } else {
              window.console.log(error);
              alert(body);
            }
          });
      },
    },
    mounted() {
      const bottomSensor = document.getElementById(`bottomSensor`);

      const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
          if (!entry.isIntersecting) {
            return;
          }
          this.requestStudySummaryPage(this.pageOffset, this.pageSize)
        });
      });
      observer.observe(bottomSensor);
    },
  };
</script>
