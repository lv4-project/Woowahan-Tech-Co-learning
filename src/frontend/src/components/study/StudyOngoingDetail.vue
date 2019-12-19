<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1>
          제목
          {{ studyInfo.subject }}
        </h1>
      </v-col>
      <v-col cols="12">
        발제자 : {{ studyInfo.presenter.nickName }}
      </v-col>
      <v-col cols="12">
        총 모집 인원 : {{ studyInfo.totalNumberOfRecruitment }}
      </v-col>
      <v-col cols="12">
        시작 날짜 : {{ studyInfo.startDate }}
      </v-col>
      <v-col cols="12">
        끝나는 날짜 : {{ studyInfo.endDate }}
      </v-col>
      <v-col cols="12">
        위치 : {{ studyInfo.location }}
      </v-col>
      <v-col cols="12">
        스터디 설명 : {{ studyInfo.description }}
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        Study Outputs
      </v-col>
    </v-row>

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
            v-for="(output, i) in studyInfo.studyOutput"
            :key="i + output.title"
          >
            <v-expansion-panel-header>
              {{ output.title }}
            </v-expansion-panel-header>
            <v-expansion-panel-content>
              <vue-markdown>
                {{ output.contents }}
              </vue-markdown>

              <v-card-actions>
                <v-spacer/>
                <v-btn
                  @click="removeOutput(output.id)"
                  color="error"
                >
                  삭제
                </v-btn>
                <v-btn
                  @click="editOutput(output.id)"
                  color="primary"
                >
                  수정
                </v-btn>
              </v-card-actions>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
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

        studyInfo: {},
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
