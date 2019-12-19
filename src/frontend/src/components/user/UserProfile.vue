<template>
  <v-container>
    <v-row justify="space-around">
      <v-avatar size="100">
        <img
          src="@/assets/defaultProfile.jpeg"
          alt="John"
        >
      </v-avatar>
    </v-row>

    <v-row justify="center">
      <v-col cols="10" sm="6" md="3">
        <v-text-field
          v-model="nickName"
          label="닉네임"
          readonly
          outlined
        />

        <v-text-field
          v-model="email"
          label="이메일"
          readonly
          outlined
        />

      </v-col>
    </v-row>
    <v-row justify="center">
      <v-btn @click="turnIntoEdit">정보 수정</v-btn>
    </v-row>
  </v-container>
</template>

<script>
  `use strict`;

  export default {
    name: `UserProfile`,
    data() {
      return {
        nickName: `aa`,
        email: ``,
      }
    },
    props: {
      userId: Number,
    },
    methods: {
      turnIntoEdit() {
        this.$router.push({name: `UserUpdateForm`});
      },
      getCookie(cookie_name) {
        let x, y;
        const val = document.cookie.split(';');

        for (let i = 0; i < val.length; i++) {
          x = val[i].substr(0, val[i].indexOf('='));
          y = val[i].substr(val[i].indexOf('=') + 1);
          x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
          if (x == cookie_name) {
            return unescape(y); // unescape로 디코딩 후 값 리턴
          }
        }
      }
    },
    mounted() {
      const temp = this;

      this.$request.get({
        url: `${window.location.origin}/api/users/myinfo`,
      }, function (error, response, body) {
        if ((response && response.statusCode) === 200) {
          body = JSON.parse(body);
          temp.nickName = body.nickName;
          temp.email = body.email;
        }
        // TODO: error
      });
    },
  };
</script>
