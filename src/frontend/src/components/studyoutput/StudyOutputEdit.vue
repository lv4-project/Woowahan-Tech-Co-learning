<template>
  <v-container>
    <v-row>
      <v-col>
        <v-text-field v-model="output.title"/>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <VueSimplemde
            :configs=mdConfigs
            v-model="output.contents"
            preview-class="markdown-body"
        />
      </v-col>
    </v-row>

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

    <v-row>
      <v-col class="d-flex flex-row-reverse">
        <v-btn
            @click=editOutput
            color="primary">
          잘 들어갔고
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
  import VueSimplemde from 'vue-simplemde';

  export default {
    name: `StudyOutputEdit`,
    components: {
      VueSimplemde,
    },
    data() {
      return {
        studyId: this.$route.params.studyId,
        outputId: this.$route.params.outputId,
        output: {
          title: ``,
          contents: ``,
        },

        snackbar: false,
        snackbarText: "",

        mdConfigs: {
          spellChecker: false,
        },
      }
    },
    methods: {
      editOutput() {
        const component = this;
        if (!this.checkValid()) {
          return;
        }
        this.$request.put({
          url: `${window.location.origin}/api/outputs/${component.outputId}`,
          body: this.output,
          json: true,
        }, function (error, response, body) {
          if ((response && response.statusCode) === 200) {
            component.$router.push(`/studies/${component.studyId}`);
          } else {
            window.alert(body);
          }
        });
      },
      checkValid() {
        if (this.output.title.length === 0) {
          this.snackbar = true;
          this.snackbarText = `제목 0자는 좀;;;;;`;
          return false;
        }

        if (this.output.contents.length < 5) {
          this.snackbar = true;
          this.snackbarText = `아니 그래도 5자는 적어야지;;;;`;
          return false;
        }
        return true;
      },
    },
    created() {
      const component = this;
      if (this.outputId != null) {
        this.$request.get(`${window.location.origin}/api/outputs/${this.outputId}`,
          function (error, response, body) {
            component.output = JSON.parse(body);
          });
      }
    },
  }
</script>

<style scoped>
  @import '~simplemde/dist/simplemde.min.css';
  @import '~github-markdown-css';
</style>