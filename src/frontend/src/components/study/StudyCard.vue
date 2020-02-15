<template>
  <v-card
    @click="showDetail(study.id)"
    class="mb-1"
    outlined
    elevation="1"
  >
    <v-skeleton-loader
      type="article"
      class="mx-auto"
      :loading="loading"
    >

      <v-row transition="scale-transition">
        <v-list-item three-line>
          <v-list-item-avatar
            size="80"
            class="mt-6"
          >
            <img :alt="study.subject" src="../../assets/default_study_profile.jpeg"/>
          </v-list-item-avatar>
          <v-list-item-content class="py-0">
            <v-row dense>
              <v-spacer/>
              <v-col cols="auto" class="me-2">
                {{ study.studyStatus }}
              </v-col>
            </v-row>
            <v-divider/>
            <v-list-item-title>{{ study.subject }}</v-list-item-title>
            <v-list-item-subtitle>by {{study.presenterName}}</v-list-item-subtitle>
            <v-list-item-content>{{ study.summary }}</v-list-item-content>
          </v-list-item-content>
        </v-list-item>
      </v-row>

      <v-divider/>

      <v-row dense>
        <v-col
          cols="auto"
          class="ml-2 grey--text text--darken-2"
        >
          <v-icon>mdi-map-marker</v-icon>
          {{ study.location }}
        </v-col>
        <v-spacer/>
        <v-col
          cols="auto"
          class="me-2"
        >
          <v-icon color="black">mdi-account-arrow-left</v-icon>
          {{ study.numberOfParticipants }} / {{ study.totalNumberOfRecruitment }}
        </v-col>
      </v-row>
    </v-skeleton-loader>
  </v-card>
</template>

<script>
  export default {
    name: "StudyCard",
    props: {
      study: Object,
      loading: Boolean,
    },
    methods: {
      showDetail(studyId) {
        this.$router.push(`studies/${studyId}`)
      },
    }
  }
</script>

<style scoped>

</style>