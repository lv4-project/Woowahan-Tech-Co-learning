<template>
  <v-container>
    <v-col
      cols="12"
      sm="6"
      md="3"
    >
      <v-text-field
        v-model="signUpFormData.email"
        label="이메일"
        outlined
      />

      <v-text-field
        v-model="signUpFormData.password"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="showPassword ? 'text' : 'password'"
        @click:append="showPassword = !showPassword"
        name="input-10-1"
        label="패스워드 입력"
        hint="최소 8 글자 이상 입력하세요."
        counter
        outlined
      />

      <v-text-field
        :append-icon="showPasswordConfirm ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.passwordMatch]"
        :type="showPasswordConfirm ? 'text' : 'password'"
        @click:append="showPasswordConfirm = !showPasswordConfirm"
        name="input-10-1"
        label="패스워드 확인"
        hint="패스워드를 다시 입력하세요."
        counter
        outlined
      />

      <v-text-field
        v-model="signUpFormData.nickName"
        label="닉네임"
        outlined
      />

      <v-col
        cols="12"
        sm="6"
        md="3"
      >
        <v-btn
          @click="requestSignUp"
          block
          tile
          color="grey"
          dark
        >
          가입
        </v-btn>
      </v-col>

      <v-col
        cols="12"
        sm="6"
        md="3"
      >
        <v-btn
          @click="cancel"
          block
          tile
          color="grey"
          dark
        >
          취소
        </v-btn>
      </v-col>
    </v-col>
  </v-container>
</template>

<script>
  `use strict`;

  export default {
    name: 'SignUpForm',
    data() {
      return {
        showPassword: false,
        showPasswordConfirm: false,
        signUpFormData: {
          email: '',
          password: '',
          nickName: '',
        },
        rules: {
          required: value => !!value || '필수 항목입니다.',
          min: v => v.length >= 8 || '최소 8 글자 이상이어야 합니다.',
          passwordMatch: v => v === this.signUpFormData.password || '비밀번호가 일치하지 않습니다.',
        },
      }
    },
    methods: {
      requestSignUp() {
        const vue = this;

        const request = require('request');
        request.post({
          url: `${window.location.origin}/api/users/signup`,
          body: this.signUpFormData,
          json: true,
        }, function (error, response, body) {
          if ((response && response.statusCode) === 200) {
            vue.$router.push(`/login`);
          } else {
            window.confirm(body);
          }
        });
      },
      cancel() {
        this.$router.push(`/login`);
      },
    },
  };
</script>
