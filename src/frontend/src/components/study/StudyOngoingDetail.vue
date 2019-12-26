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
                  {{ studyInfo.numberOfParticipants }} / {{ studyInfo.totalNumberOfRecruitment }}
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
                <v-list v-for="participant in studyInfo.participants" :key="participant.id">
                  <v-list-item @click="() => {}">
                    <v-list-item-action>
                      <v-icon>mdi-account</v-icon>
                    </v-list-item-action>
                    <v-list-item-subtitle>{{ participant.email }}</v-list-item-subtitle>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-menu>
          </v-col>
        </v-row>

        <v-divider/>

        <v-row class="my-10">
          <v-col class="black--text">
            <vue-markdown>
              {{ studyInfo.description }}
            </vue-markdown>
          </v-col>
        </v-row>

        <LocationHistoryBtn :studyId="studyId"/>

      </v-card-text>
      <v-card-actions>
        <template v-if="studyInfo.studyParticipantStatus === `presenter`">
          <v-btn
            @click="startDialog = true"
            v-if="studyInfo.studyStatus === `recruiting`"
            text
            color="primary"
          >
            START STUDY
          </v-btn>
          <v-btn
            @click="finishDialog = true"
            v-if="studyInfo.studyStatus === `ongoing`"
            text
            color="primary"
          >
            FINISH STUDY
          </v-btn>
          <v-spacer/>
          <v-btn
            @click="removeStudyDialog = true"
            text
            color="error"
          >
            DROP STUDY
          </v-btn>
        </template>

        <template>
          <v-row justify="center">
            <v-btn
              v-if="studyInfo.studyParticipantStatus === `nonParticipant`"
              @click="participantStudyDialog = true"
              text
              color="primary"
            >
              JOIN STUDY
            </v-btn>
            <v-btn
              v-if="studyInfo.studyParticipantStatus === `participant`"
              @click="withdrawDialog = true"
              text
              color="error"
            >
              EXIT STUDY
            </v-btn>
          </v-row>
        </template>
      </v-card-actions>
    </v-card>

    <v-row>
      <v-col class="d-flex flex-row-reverse">
        <v-btn
          v-if="studyInfo.studyParticipantStatus === `presenter`"
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
                    @click="changeIdAndDialog(output.id)"
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

    <v-dialog v-model="startDialog" width="80vw">
      <v-card>
        <v-card-title class="headline">
          스터디 시작
        </v-card-title>
        <v-card-text>
          정말로 시작하겠습니까?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="startStudy">예</v-btn>
          <v-btn color="primary" text @click="startDialog = false">아니오</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="finishDialog" width="80vw">
      <v-card>
        <v-card-title class="headline">
          스터디 종료
        </v-card-title>
        <v-card-text>
          다시 스터디를 시작할 수 없습니다. 정말로 끝내시겠습니까?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="finishStudy">예</v-btn>
          <v-btn color="primary" text @click="finishDialog = false">아니오</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="participantStudyDialog" width="80vw">
      <v-card>
        <v-card-title class="headline">
          스터디 참가
        </v-card-title>
        <v-card-text>
          정말로 참가하시겠습니까?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="participateInStudy">예</v-btn>
          <v-btn color="primary" text @click="participantStudyDialog = false">아니오</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="withdrawDialog" width="80vw">
      <v-card>
        <v-card-title class="headline">
          스터디 탈퇴
        </v-card-title>
        <v-card-text>
          정말로 탈퇴하시겠습니까?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="withdraw">예</v-btn>
          <v-btn color="primary" text @click="withdrawDialog = false">아니오</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="removeStudyDialog" width="80vw">
      <v-card>
        <v-card-title class="headline">
          스터디 삭제
        </v-card-title>
        <v-card-text>
          지금까지 작성하신 내용이 모두 사라집니다. 정말로 삭제하시겠습니까?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="removeStudy">예</v-btn>
          <v-btn color="primary" text @click="removeStudyDialog = false">아니오</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="outputDialog" width="80vw">
      <v-card>
        <v-card-title class="headline">
          산출물 삭제
        </v-card-title>
        <v-card-text>
          지금까지 작성하신 내용이 모두 사라집니다. 정말로 삭제하시겠습니까?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="removeOutput">예</v-btn>
          <v-btn color="primary" text @click="outputDialog = false">아니오</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
  import VueMarkdown from 'vue-markdown';
  import LocationHistoryBtn from "../map/LocationHistoryBtn";
  import {eventBus} from "../../main";

  export default {
    name: "StudyDetail",
    components: {
      LocationHistoryBtn,
      VueMarkdown,
    },
    data() {
      return {
        studyId: this.$route.params.studyId,
        studyInfo: {},
        menu: ``,
        startDialog: false,
        finishDialog: false,
        participantStudyDialog: false,
        withdrawDialog: false,
        removeStudyDialog: false,
        outputDialog: false,
        id: 0,
      }
    },
    methods: {
      changeIdAndDialog(id) {
        this.outputDialog = true;
        this.id = id;
      },
      writeStudyOutput() {
        this.$router.push(`/studies/${this.studyInfo.id}/outputs`)
      },
      removeOutput() {
        this.$request.delete(`${window.location.origin}/api/outputs/${this.id}`, (error, response, body) => {
            if (response.statusCode === 200) {
              this.removeStudyDialog = false;
              this.$request.get(`${window.location.origin}/api/studies/${this.studyId}/outputs`,
                (response, body) => {
                  this.studyInfo.studyOutput = JSON.parse(body);
                });
            } else if (response.statusCode === 400) {
              eventBus.$emit(`raiseNotice`, body);
            }
          });
      },
      editOutput(outputId) {
        this.$router.push(`/studies/${this.studyId}/outputs/${outputId}`);
      },
      participateInStudy() {
        this.$request.post(`${window.location.origin}/api/studies/${this.studyId}/participants`,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.participantStudyDialog = false;
              this.loadStudyDetail();
            } else if (response.statusCode === 400) {
              eventBus.$emit(`raiseNotice`, `참여할 수 없는 스터디입니다.`);
            }
          });
      },
      withdraw() {
        this.$request.delete(`${window.location.origin}/api/studies/${this.studyId}/participants`,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.withdrawDialog = false;
              this.loadStudyDetail();
            } else if (response.statusCode === 400) {
              eventBus.$emit(`raiseNotice`, body);
            }
          });
      },
      loadStudyDetail() {
        this.$request.get(`${window.location.origin}/api/studies/${this.studyId}`,
          (error, response, body) => {
            if ((response && response.statusCode) === 200) {
              this.studyInfo = JSON.parse(body);
            } else if (response.statusCode === 401) {
              this.$router.push(`/login`)
            } else {
              eventBus.$emit(`raiseNotice`, `그런 Study 없음`);
              window.history.back();
            }
          });
      },
      startStudy() {
        this.$request.patch(`${window.location.origin}/api/studies/${this.studyId}/start`,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.studyInfo = JSON.parse(body);
              this.startDialog = false;
              window.console.log(this.studyInfo);
            } else if (response.statusCode === 401) {
              this.$router.push(`/login`)
            } else {
              eventBus.$emit(`raiseNotice`, `그런 Study 없음`);
              window.history.back();
            }
          });
      },
      finishStudy() {
        this.$request.patch(`${window.location.origin}/api/studies/${this.studyId}/finish`,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.finishDialog = false;
              this.studyInfo = JSON.parse(body);
            } else if (response.statusCode === 401) {
              this.$router.push(`/login`)
            } else {
              eventBus.$emit(`raiseNotice`, `그런 Study 없음`);
              window.history.back();
            }
          });
      },
      removeStudy() {
        this.$request.delete(`${window.location.origin}/api/studies/${this.studyId}`,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.removeStudyDialog = false;
              this.$router.push(`/recruitment`)
            } else if (response.statusCode === 400) {
              eventBus.$emit(`raiseNotice`, body);
            }
          });
      },
    },
    created() {
      this.loadStudyDetail();
    },
  }
</script>
