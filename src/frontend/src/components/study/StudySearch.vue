<template>
  <div>
    <v-row justify="center">
      <v-col cols="auto">
        <v-radio-group
          v-model="status"
          hide-details
          row
        >
          <v-radio
            label="Recruiting"
            value="recruiting"
          />
          <v-radio
            label="Ongoing"
            value="ongoing"
          />
          <v-radio
            label="Finished"
            value="finished"
          />
        </v-radio-group>
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-col class="pr-0">
        <v-text-field
          v-model="keyword"
          class="me-0"
          label="Search Study"
          placeholder="키워드를 입력해주세요."
          hide-details
        />
      </v-col>
      <v-col
        class="pl-0"
        cols="auto"
        align-self="end"
      >
        <v-btn
          @click="firstSearch()"
          icon
        >
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <StudyCard
      v-for="study in studies"
      :key="study.id"
      :study="study"
    />

    <v-row>
      <v-col>
        <v-snackbar
          v-model="snackbar"
          :timeout="2000"
        >
          {{ snackbarText }}
          <v-btn
            color="blue"
            text
            @click="snackbar = false"
          >
            Close
          </v-btn>
        </v-snackbar>
      </v-col>
    </v-row>

    <div id="bottomSensor"></div>
  </div>
</template>

<script>
  import StudyCard from "./StudyCard";

  export default {
    name: "StudySearch",
    components: {StudyCard},
    data() {
      return {
        keyword: ``,
        fixedKeyword: ``,

        status: `recruiting`,
        fixedStatus: ``,

        studies: [],
        pageOffset: 0,
        pageSize: 2,

        snackbar: false,
        snackbarText: "",
      }
    },
    methods: {
      checkValid() {
        if (this.keyword.length < 2) {
          this.snackbar = true;
          this.snackbarText = `검색어를 2자 이상 작성해주세요.`;
          return true;
        }
        return false;
      },
      firstSearch() {
        if (this.checkValid()) {
          return;
        }

        this.pageOffset = 0;
        this.fixedKeyword = this.keyword;
        this.fixedStatus = this.status;

        const request = `${window.location.origin}/api/studies/search/${this.fixedStatus}?` +
          `keyword=${this.fixedKeyword}&page=${this.pageOffset}&size=${this.pageSize}`;

        this.$request.get(request,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.studies = JSON.parse(body);
              this.observeSearch();
            } else {
              window.console.log(error);
              alert(body);
            }
          });
      },
      observeSearch() {
        const bottomSensor = document.getElementById(`bottomSensor`);

        const observer = new IntersectionObserver(entries => {
          entries.forEach(entry => {
            if (!entry.isIntersecting) {
              return;
            }
            this.nextSearch()
          });
        });
        observer.observe(bottomSensor);
      },
      nextSearch() {
        const request = `${window.location.origin}/api/studies/search/${this.fixedStatus}?` +
          `keyword=${this.fixedKeyword}&page=${++this.pageOffset}&size=${this.pageSize}`;

        this.$request.get(request,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.studies = this.studies.concat(JSON.parse(body));
            } else {
              window.console.log(error);
              alert(body);
            }
          });
      },
    },
  }
</script>
