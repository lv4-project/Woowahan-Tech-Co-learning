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

    <v-col cols="12" sm="6" md="3">
      <v-text-field
        v-model="nickName"
        label="닉네임"
        outlined
      />

      <v-text-field
        v-model="email"
        label="이메일"
        readonly
        outlined
      />

      <div class="text-center">
        <v-btn @click="requestUpdate" block tile color="grey" dark>수정</v-btn>
      </div>
    </v-col>
  </v-container>
</template>

<script>
  `use strict`;

  export default {
    name: `UserUpdateForm`,
    data() {
      return {
        nickName: ``,
        email: ``,
      }
    },
    props: {
      userId: Number,
    },
    methods: {
      requestUpdate: function () {
        const request = require('request');
        request.put({
          url: `${window.location.origin}/api/users/`,
          body: { nickName: this.nickName },
          json: true,
        }, function (error, response) {
          if ((response && response.statusCode) === 200) {
            // TODO: success
          }
          // TODO: error
        });
      }
    },
    mounted() {
      let temp = this;
      const request = require('request');
      request.get({
        url: `${window.location.origin}/api/users/${this.userId}`,
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
