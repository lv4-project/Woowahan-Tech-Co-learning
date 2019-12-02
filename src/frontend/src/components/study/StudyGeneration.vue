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
        label="Subject"
        required
      />

      <v-text-field
        v-model="presenter.name"
        :counter="presenter.length"
        :rules="presenter.rules"
        label="Presenter"
        required
      />

      <v-text-field
        v-model="personnel.init"
        :rules="personnel.rules"
        :maxlength="personnel.max"
        label="Personal"
        type="text"
        required
      />

      <v-text-field
        v-model="period.init"
        :rules="period.rules"
        :maxlength="period.max"
        label="Period"
        type="text"
        required
      />

      <v-textarea
        v-model="description"
        label="Description"
        required
      />

      <v-btn
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

  export default {
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
        presenter: {
          name: ``,
          length: 10,
          rules: [
            v => validateBlank(v, `이름을 입력해주세요.`),
            v => validateLength(v, this.presenter.length, `이름은 ${this.presenter.length}글자 이하로 입력해주세요.`),
          ],
        },
        personnel: {
          init: ``,
          rules: [
            v => validateBlank(v, `인원 수를 입력해주세요.`),
          ],
          max: 2,
        },
        period: {
          init: ``,
          rules: [
            v => validateBlank(v, `기간을 입력해주세요.`),
          ],
          max: 2,
        },
        description: ``,
        checkbox: false,
      }
    },
  }
</script>