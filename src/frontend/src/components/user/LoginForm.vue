<template>
  <v-container>
    <v-col
      cols="12"
      sm="6"
      md="3"
    >
      <v-text-field
        v-model="loginData.email"
        label="이메일"
        outlined
      />

      <v-text-field
        v-model="loginData.password"
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
    </v-col>

    <v-col
      cols="12"
      sm="6"
      md="3"
    >
      <v-btn
        @click="login"
        block
        tile
        color="grey"
        dark
      >
        로그인
      </v-btn>
    </v-col>

    <v-col
      cols="12"
      sm="6"
      md="3"
    >
      <v-btn
        @click="showSignUp"
        block
        tile
        color="grey"
        dark
      >
        회원가입
      </v-btn>
    </v-col>

    <v-col
      cols="12"
      sm="6"
      md="3"
    >
      <v-row justify="center">
        <a href="https://github.com/login/oauth/authorize?client_id=ec77f51776eab7f7dcf1&scope=user:email">
          Github 로그인
        </a>
      </v-row>
    </v-col>

  </v-container>
</template>

<script>
  export default {
    name: 'LoginForm',
    data() {
      return {
        showPassword: false,
        loginData: {
          email: '',
          password: '',
        },
        rules: {
          required: value => !!value || '필수 항목입니다.',
          min: v => v.length >= 8 || '최소 8 글자 이상이어야 합니다.',
        },
      }
    },
    computed: {
      isInRecruitmentView() {
        return this.$route.name === `StudyRecruitment`;
      },
    },
    methods: {
      login() {
        const vue = this;

        const request = require('request');
        request.post({
          url: `${window.location.origin}/api/oauth/login`,
          body: this.loginData,
          json: true,
        }, function (error, response, body) {
          if ((response && response.statusCode) === 200) {
            vue.$router.push(`/recruitment`);
          } else {
            window.alert(body);
          }
        });
      },
      showSignUp() {
        this.$router.push(`/signup`)
      },
    },
  };
</script>
Co
