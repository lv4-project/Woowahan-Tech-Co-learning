<template>
  <v-container>
    <v-row justify="center" align="center"
           style="height: 600px;">
      <v-col>
        <v-row>
          <v-col align="center">
            <h1>테 코 러 닝</h1>
          </v-col>
        </v-row>
        <v-row>
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
        </v-row>

        <v-row>
          <v-col
            cols="12"
            sm="6"
            md="3"
          >
            <v-btn
              @click="login"
              block
              tile
              color="primary"
              dark
            >
              로그인
            </v-btn>
          </v-col>
        </v-row>

        <v-row>
          <v-col
            cols="12"
            sm="6"
            md="3"
          >
            <v-btn
              href="https://github.com/login/oauth/authorize?client_id=ec77f51776eab7f7dcf1&scope=user:email"
              block
              tile
              color="black"
              dark
            >
              Github 로그인
            </v-btn>
          </v-col>
        </v-row>

        <v-row>
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
        </v-row>
      </v-col>
    </v-row>

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
        this.$request.post({
          url: `${window.location.origin}/api/oauth/login`,
          body: this.loginData,
          json: true,
        }, (error, response, body) => {
          if ((response && response.statusCode) === 200) {
            this.$router.data.auth = true;
            this.$router.push(`/recruitment`);
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
