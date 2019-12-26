<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="auto">
        <h1>회원가입</h1>
      </v-col>
    </v-row>

    <div style="height: 30px;"></div>

    <v-row justify="center">
      <v-col cols="auto">
        <v-text-field
          v-model="signUpFormData.email"
          :rules="[rules.required, rules.emailRules]"
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
          :rules="[rules.required]"
          label="닉네임"
          outlined
        />

        <v-row>
          <v-col>
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
        </v-row>

        <v-row>
          <v-col>
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
        </v-row>
      </v-col>
    </v-row>

    <SnackBar/>
  </v-container>
</template>

<script>
  `use strict`;

  import {eventBus} from "../../main";
  import SnackBar from "../templates/SnackBar";

  export default {
    name: 'SignUpForm',
    components: {SnackBar},
    data() {
      return {
        showPassword: false,
        showPasswordConfirm: false,
        signUpFormData: {
          email: ``,
          password: ``,
          nickName: ``,
        },
        rules: {
          required: value => !!value || `필수 항목입니다.`,
          min: v => v.length >= 8 || `최소 8 글자 이상이어야 합니다.`,
          passwordMatch: v => v === this.signUpFormData.password || `비밀번호가 일치하지 않습니다.`,
          emailRules: v => /.+@.+/.test(v) || `이메일이 아닙니다.`,
        },
      }
    },
    methods: {
      requestSignUp() {
        this.$request.post(
          {
            url: `${window.location.origin}/api/users/signup`,
            body: this.signUpFormData,
            json: true,
          },
          (error, response, body) => {
            if (response.statusCode === 200) {
              eventBus.$emit(`raiseNotice`, `회원가입에 성공하였습니다. 로그인 해주세요`);
              this.$router.push(`/login`);
              return;
            }

            eventBus.$emit(`raiseNotice`, body);
          });
      },
      cancel() {
        this.$router.push(`/login`);
      },
    },
  };
</script>
