<template>
  <v-container>
    <v-row>
      <v-col align="center">
        <h1>{{ nickName }}'s Profile</h1>
      </v-col>
    </v-row>

    <v-row justify="space-around">
      <v-avatar size="100">
        <img
          src="@/assets/defaultProfile.jpeg"
          alt="John"
        >
      </v-avatar>
    </v-row>

    <v-row justify="center" class="mb-0">
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
          hide-details
        />
      </v-col>
    </v-row>
    <v-row>
      <v-spacer/>
      <v-col cols="auto">
        <v-btn @click="turnIntoEdit">정보 수정</v-btn>
      </v-col>
      <v-col cols="auto">
        <v-btn @click="logout">로그아웃</v-btn>
      </v-col>
      <v-spacer/>
    </v-row>

    <v-divider/>

    <v-row v-if="timelines.length !== 0">
      <v-col>
        <v-timeline :dense="$vuetify.breakpoint.smAndDown">
          <v-timeline-item
            v-for="timeline in timelines"
            :key="timeline.id"
            :icon="'mdi-star'"
          >
            <v-card class="elevation-2">
              <v-card-title>
                {{ timeline.subject }}
              </v-card-title>
              <v-card-subtitle>
                {{ timeline.startDate }} ~ {{ timeline.endDate }}
              </v-card-subtitle>
              <v-card-text>
                {{ timeline.description }}
              </v-card-text>
            </v-card>
          </v-timeline-item>
        </v-timeline>
      </v-col>
    </v-row>
    <v-row v-else>
      <v-spacer/>
      <v-col cols="auto">
        아직 참여한 스터디가 없습니다.
      </v-col>
      <v-spacer/>
    </v-row>
  </v-container>
</template>

<script>
  `use strict`;

  export default {
    name: `UserProfile`,
    data() {
      return {
        id: ``,
        nickName: ``,
        email: ``,

        timelines: [],
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
      },
      logout() {
        this.$request.get(
          {
            url: `${window.location.origin}/api/oauth/logout`,
          },
          (error, response) => {
            if ((response && response.statusCode) === 200) {
              this.$router.push(`/login`)
            }
            // TODO: error
          });
      },

      getTimeline() {
        this.$request.get(`${window.location.origin}/api/users/${this.id}/studies`,
          (error, response, body) => {
            if ((response && response.statusCode) === 200) {
              this.timelines = JSON.parse(body);
            }
          });
      }
    },
    mounted() {
      this.$request.get({
        url: `${window.location.origin}/api/users/myinfo`,
      }, (error, response, body) => {
        if ((response && response.statusCode) === 200) {
          body = JSON.parse(body);
          this.id = body.id;
          this.nickName = body.nickName;
          this.email = body.email;

          this.getTimeline();
        }
        // TODO: error
      });


    },
  };
</script>
