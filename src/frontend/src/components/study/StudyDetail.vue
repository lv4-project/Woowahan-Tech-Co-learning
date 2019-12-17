<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1>
          제먹
          {{ detailInfo.subject }}
        </h1>
      </v-col>
      <v-col cols="12">
        발제자 : {{ detailInfo.presenter.nickName }}
      </v-col>
      <v-col cols="12">
        총 모집 인원 : {{ detailInfo.totalNumberOfRecruitment }}
      </v-col>
      <v-col cols="12">
        시작 날짜 : {{ detailInfo.startDate }}
      </v-col>
      <v-col cols="12">
        끝나는 날짜 : {{ detailInfo.endDate }}
      </v-col>
      <v-col cols="12">
        위치 : {{ detailInfo.location }}
      </v-col>
      <v-col cols="12">
        스터디 설명 : {{ detailInfo.description }}
      </v-col>
      <v-btn color="primary">
        참여할래요
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
  import {eventBus} from "../../main";

  export default {
    name: "StudyDetail",
    data() {
      return {
        detailInfo: {
          presenter: {
            nickName: ``,
          },
          subject: ``,
          totalNumberOfRecruitment: 0,
          startDate: ``,
          endDate: ``,
          location: ``,
          description: ``,
        },
      }
    },
    mounted() {
      eventBus.$on(`newRecruitmentCreated`, createdRecruitment => {
        this.detailInfo = createdRecruitment;
      });
    },
    destroyed() {
      eventBus.$off(`newRecruitmentCreated`);
    }
  }
</script>
