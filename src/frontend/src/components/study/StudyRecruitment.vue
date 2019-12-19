<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card
          v-for="study in studies"
          :key="study.id"
          class="mx-auto mb-5"
          max-width="90%"
          outlined
        >
          <v-list-item three-line>
            <v-list-item-content>
              <v-col
                cols="8"
                class="text-left text-truncate"
              >
                <v-list-item-title class="headline mb-1">
                  {{ study.subject }}
                </v-list-item-title>
              </v-col>
              <v-col
                cols="4"
                class="text-right pa-0"
              >
                인원 {{ study.numberOfParticipants }} / {{ study.totalNumberOfRecruitment }}
              </v-col>
              <v-col cols="9">
                <v-list-item-subtitle>
                  {{ study.summary }}
                </v-list-item-subtitle>
              </v-col>
              <v-col
                cols="3"
                class="pa-0"
              >
                <v-card-actions class="pa-0 float-right">
                  <v-btn
                    @click="showDetail(study.id)"
                    class="pa-1"
                    color="primary"
                  >
                    상세 정보
                  </v-btn>
                </v-card-actions>
              </v-col>
            </v-list-item-content>
          </v-list-item>
        </v-card>
        <div id="bottomSensor"></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  `use strict`;

  export default {
    name: 'StudyRecruitment',
    data() {
      return {
        studies: [],
        pageOffset: 0,
        pageSize: 5,
      }
    },
    methods: {
      requestStudySummaryPage(pageOffset, pageSize) {
        const component = this;
        const request = `${window.location.origin}/api/studies/summary/participating?page=${String(pageOffset)}&size=${String(pageSize)}&sort=createdDate,desc`;

        this.$request.get(request, function (error, response, body) {
          if (response.statusCode === 200) {
            component.studies = component.studies.concat(JSON.parse(body));
            component.pageOffset++;
          } else {
            window.console.log(error);
            alert(body);
          }
        });
      },
      showDetail(studyId) {
        this.$router.push(`studies/${studyId}`)
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
    }
  }
</script>
