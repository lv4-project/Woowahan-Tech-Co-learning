<template>
  <v-container>
    <v-card
      class="mx-auto"
      elevation="1"
    >
      <v-card-text>
        <v-row dense>
          <v-col cols="auto">
            <h1 class="black--text">
              {{ studyInfo.subject }}
            </h1>
          </v-col>
          <v-spacer/>
          <v-col cols="auto">
            <v-chip outlined>
              <v-icon
                color="indigo"
                left
              >
                mdi-account
              </v-icon>
              {{ studyInfo.presenter.nickName }}
            </v-chip>
          </v-col>
        </v-row>

        <v-row dense>
          <v-col cols="auto">
            <p>{{ studyInfo.startDate }} ~ {{ studyInfo.endDate }}</p>
          </v-col>
          <v-spacer/>
          <v-col cols="auto">
            <v-menu
              v-model="menu"
              transition="scale-transition"
              origin="top right"
            >
              <template v-slot:activator="{ on }">
                <v-chip
                  pill
                  v-on="on"
                >
                  <v-icon left>
                    mdi-account-group
                  </v-icon>
                  2 / {{ studyInfo.totalNumberOfRecruitment }}
                </v-chip>
              </template>
              <v-card width="300">
                <v-list dark>
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>Joined Crew</v-list-item-title>
                    </v-list-item-content>
                    <v-list-item-action>
                      <v-btn
                        icon
                        @click="menu = false"
                      >
                        <v-icon>mdi-close-circle</v-icon>
                      </v-btn>
                    </v-list-item-action>
                  </v-list-item>
                </v-list>
                <v-list>
                  <v-list-item @click="() => {}">
                    <v-list-item-action>
                      <v-icon>mdi-account</v-icon>
                    </v-list-item-action>
                    <v-list-item-subtitle>kingducksu@yogi.yo</v-list-item-subtitle>
                  </v-list-item>
                </v-list>
                <v-list>
                  <v-list-item @click="() => {}">
                    <v-list-item-action>
                      <v-icon>mdi-account</v-icon>
                    </v-list-item-action>
                    <v-list-item-subtitle>mir@dduho.dev</v-list-item-subtitle>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-menu>
          </v-col>
        </v-row>


        <v-divider/>

        <v-row>
          <v-col class="black--text">
            <vue-markdown>
              {{ studyInfo.description }}
            </vue-markdown>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-icon>mdi-map-marker</v-icon>
            {{ studyInfo.location }}
          </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions v-if="studyInfo.status === `RECRUITING`">
        <template v-if="studyInfo.studyParticipantStatus === `presenter`">
          <v-btn
            text
            color="primary"
          >
            START STUDY
          </v-btn>
          <v-spacer/>
          <v-btn
            text
            color="error"
          >
            DROP STUDY
          </v-btn>
        </template>

        <template
          v-if="studyInfo.studyParticipantStatus === `participant`"
        >
          <v-row justify="center">
            <v-btn
              text
              color="error"
            >
              EXIT STUDY
            </v-btn>
          </v-row>
        </template>

        <template v-if="studyInfo.studyParticipantStatus === `nonParticipant`">
          <v-row justify="center">
            <v-btn
              @click="participateInStudy"
              text
              color="primary"
            >
              JOIN STUDY
            </v-btn>
          </v-row>
        </template>
      </v-card-actions>
    </v-card>

    <v-row>
      <v-col class="d-flex flex-row-reverse">
        <v-btn
          @click="writeStudyOutput"
          color="primary"
        >
          Output 작성
        </v-btn>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <v-expansion-panels :multiple=true>
          <v-expansion-panel
            v-for="output in studyInfo.studyOutput"
            :key="output.id"
          >
            <v-expansion-panel-header>
              {{ output.title }}
            </v-expansion-panel-header>
            <v-expansion-panel-content>
              <vue-markdown>
                {{ output.contents }}
              </vue-markdown>

              <v-row>
                <v-spacer/>
                <v-col cols="auto">
                  <v-btn
                    @click="editOutput(output.id)"
                    icon
                    text
                  >
                    <v-icon>mdi-square-edit-outline</v-icon>
                  </v-btn>
                </v-col>
                <v-col cols="auto">
                  <v-btn
                    @click="removeOutput(output.id)"
                    icon
                    text
                  >
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>

    <v-row v-if="studyInfo.studyParticipantStatus === `presenter`">
      <v-btn
        @click="showMap"
        color="primary"
      >
        위치 추가하기
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
  import VueMarkdown from 'vue-markdown';

  export default {
    name: "StudyDetail",
    components: {
      VueMarkdown,
    },
    data() {
      return {
        studyId: this.$route.params.studyId,
        studyInfo: {presenter: {},},
        menu: ``,
      }
    },
    methods: {
      writeStudyOutput() {
        this.$router.push(`/studies/${this.studyInfo.id}/outputs`)
      },
      removeOutput(outputId) {
        const component = this;
        // TODO 진짜 삭제할꺼냐 확인
        this.$request.delete(`${window.location.origin}/api/outputs/${outputId}`, function (error, response) {
          if (response.statusCode === 200) {
            // TODO 다시 조회하는 방식
            component.$request.get(`${window.location.origin}/api/studies/${component.studyId}/outputs`,
              function (error, response, body) {
                component.studyInfo.studyOutput = JSON.parse(body);
              });
          }
        });
      },
      editOutput(outputId) {
        this.$router.push(`/studies/${this.studyId}/outputs/${outputId}`);
      },
      participateInStudy() {
        this.$request.post(`${window.location.origin}/api/studies/${this.studyId}/participants`,
          function (error, response, body) {
            if (response.statusCode === 200) {
              this.studyInfo.studyParticipantStatus = body;
            }
          });
      },
      showMap() {
        this.$router.push({name: `Map`});
      }
    },
    created() {
      const component = this;
      this.$request.get(`${window.location.origin}/api/studies/${component.studyId}`,
        function (error, response, body) {
          if ((response && response.statusCode) === 200) {
            component.studyInfo = JSON.parse(body);
          } else if (response.statusCode === 401) {
            component.$router.push(`/login`)
          } else {
            // TODO snackbar로 대체
            window.alert("그런 study 없음");
            window.history.back();
          }
        });
    },
  }
</script>
