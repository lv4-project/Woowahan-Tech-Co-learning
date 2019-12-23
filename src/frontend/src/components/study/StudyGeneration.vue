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

      <v-row>
        <v-col cols="6">
          <v-menu
            v-model="startDate.view"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="startDate.init"
                label="시작 날짜"
                readonly
                v-on="on"
              />
            </template>
            <v-date-picker
              v-model="startDate.init"
              @input="startDate.view = false"
            />
          </v-menu>
        </v-col>
        <v-spacer/>
        <v-col cols="6">
          <v-menu
            v-model="endDate.view"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="endDate.init"
                label="끝나는 날짜"
                readonly
                v-on="on"
              />
            </template>
            <v-date-picker
              v-model="endDate.init"
              @input="endDate.view = false"
            />
          </v-menu>
        </v-col>
      </v-row>

      <v-text-field
        v-model="location"
        label="장소"
        type="text"
        required
      />

      <VueSimplemde
        :sanitize=true
        :configs=mdConfigs
        v-model="description"
        preview-class="markdown-body"
      />

      <v-card-actions>
        <v-spacer/>
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
      </v-card-actions>
    </v-form>
  </v-container>
</template>

<script>
  `use strict`;
  import VueSimplemde from 'vue-simplemde';

  export default {
    name: "StudyGeneration",
    components: {
      VueSimplemde,
    },
    data() {
      const validateBlank = (v, message) => !!v || message;
      const validateLength = (v, length, message) => (v && v.length <= length) || message;

      return {
        valid: true,

        date: new Date().toISOString().substr(0, 10),
        menu: false,
        modal: false,
        menu2: false,

        mdConfigs: {
          spellChecker: false,
        },

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
          view: ``,
          init: ``,
          rules: [
            v => validateBlank(v, `기간을 입력해주세요.`),
          ],
          max: 10,
        },
        endDate: {
          view: ``,
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
            component.$router.push(`/studies/${body.id}`);
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