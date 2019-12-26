<template>
  <v-bottom-navigation
    fixed
    grow
    shift
  >
    <v-btn
      @click="routeToStudyGeneration"
      icon
    >
      <span>발제</span>
      <v-icon>mdi-square-edit-outline</v-icon>
    </v-btn>

    <v-menu top :offset-y="true" auto>
      <template v-slot:activator="{ on }">
        <v-btn
          icon
          v-on="on"
        >
          <span>{{ recent.name }}</span>
          <v-icon>{{ recent.icon }}</v-icon>
        </v-btn>
      </template>

      <v-list nav>
        <v-btn
          v-for="btn in studyButtons"
          :key="btn.name"
          @click="changeComponent(btn)"
          fab
          icon
        >
          <v-icon>{{ btn.icon }}</v-icon>
          <span>{{ btn.name }}</span>
        </v-btn>
      </v-list>
    </v-menu>

    <v-btn
      @click="changeView(`StudySearch`)"
      icon
    >
      <span>검색</span>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn
      @click="changeView(`UserProfile`)"
      icon
    >
      <span>프로필</span>
      <v-icon>mdi-account</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
  export default {
    name: "BottomNavigation",
    data() {
      return {
        studyButtons: [
          {
            name: `모집`,
            component: `StudyRecruitment`,
            icon: `mdi-account-group`,
          },
          {
            name: `진행`,
            component: `StudyOngoing`,
            icon: `mdi-airplane-takeoff`,
          },
          {
            name: `완료`,
            component: `StudyFinished`,
            icon: `mdi-check`,
          },
        ],

        recent: {
          name: `모집`,
          component: `StudyRecruitment`,
          icon: `mdi-account-group`,
        },
      }
    },
    methods: {
      changeComponent(btn) {
        this.recent = btn;
        this.$router.push({name: btn.component})
      },
      changeView(componentName) {
        this.$router.push({name: componentName})
      },
      routeToStudyGeneration() {
        this.$router.push(`/generation`)
      },
    },
    updated() {
      this.$router.push({name: this.component});
    },
  }
</script>

<style scoped>

</style>