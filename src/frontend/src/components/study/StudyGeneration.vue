<template>
  <v-container>
    <v-form
      v-model="valid"
      ref="form"
    >
      <v-text-field
        v-model="subject.name"
        :counter="subject.length"
        :rules="subject.rules"
        label="스터디 주제"
        required
      />

      <v-text-field
        v-model="totalNumberOfRecruitment.init"
        :rules="totalNumberOfRecruitment.rules"
        :maxlength="totalNumberOfRecruitment.max"
        label="총 모집 인원"
        type="text"
        required
      />

      <v-text-field
        v-model="startDate.init"
        :rules="startDate.rules"
        :maxlength="startDate.max"
        label="시작 날짜"
        type="text"
        required
      />

      <v-text-field
        v-model="endDate.init"
        :rules="endDate.rules"
        :maxlength="endDate.max"
        label="끝나는 날짜"
        type="text"
        required
      />

      <v-text-field
        v-model="location"
        label="장소"
        type="text"
        required
      />

      <v-textarea
        v-model="description"
        label="스터디 설명"
        required
      />

      <v-btn
        @click="requestStudyGeneration"
        color="primary"
      >
        생성
      </v-btn>
      <v-btn
        color="error"
      >
        취소
      </v-btn>
    </v-form>
  </v-container>
</template>

<script>
  `use strict`;

  import {eventBus} from "../../main";

  export default {
    name: "StudyGeneration",
    data() {
      const validateBlank = (v, message) => !!v || message;
      const validateLength = (v, length, message) => (v && v.length <= length) || message;

      return {
        valid: true,
        subject: {
          name: ``,
          length: 60,
          rules: [
            v => validateBlank(v, `주제를 입력해주세요.`),
            v => validateLength(v, this.subject.length, `주제는 ${this.subject.length}글자 이하로 작성해주세요.`),
          ],
        },
        totalNumberOfRecruitment: {
          init: ``,
          rules: [
            v => validateBlank(v, `인원 수를 입력해주세요.`),
          ],
          max: 2,
        },
        startDate: {
          init: ``,
          rules: [
            v => validateBlank(v, `기간을 입력해주세요.`),
          ],
          max: 10,
        },
        endDate: {
          init: ``,
          rules: [
            v => validateBlank(v, `기간을 입력해주세요.`),
          ],
          max: 10,
        },
        location: ``,
        description: ``,
        checkbox: false,
      }
    },
    methods: {
      requestStudyGeneration() {
        const component = this;
        const studyCreationRequest = {
          uri: `${window.location.origin}/api/studies`,
          method: `POST`,
          body: {
            subject: this.subject.name,
            totalNumberOfRecruitment: this.totalNumberOfRecruitment.init,
            startDate: this.startDate.init,
            endDate: this.endDate.init,
            location: this.location,
            description: this.description,
          },
          json: true,
        };

        this.$request(studyCreationRequest, function (error, response, body) {
          if (response.statusCode === 200) {
            component.$router.push(`/study-detail`).then(() => {
              eventBus.$emit(`newRecruitmentCreated`, body);
            });
          } else if (response.statusCode === 401) {
            component.$router.push(`/login`)
          } else {
            window.alert(body);
          }
        });
      },
    },
  }
</script>