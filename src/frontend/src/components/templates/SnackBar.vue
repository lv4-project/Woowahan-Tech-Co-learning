<template>
  <v-snackbar
    v-model="show"
    :bottom="true"
    :right="true"
    :timeout="3000"
    class="mb-10"
  >
    {{ message }}
    <v-btn
      color="white"
      text
      @click="show = false"
    >
      알겠어요!
    </v-btn>
  </v-snackbar>
</template>

<script>
  import {eventBus} from "../../main";

  export default {
    name: `SnackBar`,
    data() {
      return {
        show: false,
        message: ``,
      }
    },
    created() {
      eventBus.$on(`raiseNotice`, message => {
        this.message = message;
        this.show = true;
      })
    }
  }
</script>

<style scoped>

</style>