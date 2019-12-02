<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card
          v-for="info in infos"
          :key="info.id"
          class="mx-auto mb-5"
          max-width="90%"
          outlined
        >
          <v-list-item three-line>
            <v-list-item-content>
              <v-col
                cols="8"
                class="text-left text-truncate"
              >
                <v-list-item-title class="headline mb-1">
                  {{ info.subject }}
                </v-list-item-title>
              </v-col>
              <v-col
                cols="4"
                class="text-right pa-0"
              >
                인원 {{ info.personal }} / {{ info.total }}
              </v-col>
              <v-col cols="9">
                <v-list-item-subtitle>
                  {{ info.description }}
                </v-list-item-subtitle>
              </v-col>
              <v-col
                cols="3"
                class="pa-0"
              >
                <v-card-actions class="pa-0 float-right">
                  <v-btn
                    class="pa-1"
                    color="primary"
                  >
                    상세 정보
                  </v-btn>
                </v-card-actions>
              </v-col>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  `use strict`;

  export default {
    name: 'OngoingStudy',
    data() {
      return {
        infos:
          [
            {
              id: `1`,
              subject: `토비의 스프링`,
              personal: `99`,
              total: `99`,
              description: `예제와 학습테스트를 기반으로 스프링 원리, 테스트 작성, 점진적 리팩토링 연습`,
            },
            {
              id: `2`,
              subject: `JPA 스터디`,
              personal: `99`,
              total: `99`,
              description: `미니 프로젝트 예제를 vue js와 연동해 작성해보기`,
            },
          ],
      }
    },
  };
</script>
