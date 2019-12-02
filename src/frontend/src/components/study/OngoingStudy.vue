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
                  <div class="mb-2">
                    진행도
                  </div>
                  <v-progress-linear
                    :value="info.progress"
                    color="red darken-2"
                    rounded
                    height="10px"
                  />
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
              progress: `10`,
            },
            {
              id: `2`,
              subject: `비모의 스프링`,
              personal: `99`,
              total: `99`,
              progress: `100`,
            },
          ],
      }
    },
  };
</script>
