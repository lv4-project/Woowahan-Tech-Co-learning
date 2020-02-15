<template>
  <div>
    <v-menu>
      <template v-slot:activator="{ on, attrs }">
        <v-input
          v-bind="attrs"
          v-on="on"
          @click="getPlaces"
          readonly
          dense
          hide-details
        >
          <v-icon>mdi-map-marker</v-icon>
          장소 히스토리
          <v-input
            :studyId="studyId"
            @click:append="mapDialog=!mapDialog"
            append-icon="mdi-plus"
          />
        </v-input>
      </template>

      <v-row
        id="scroll-target"
        style="max-height: 500px"
        class="overflow-y-auto"
      >
        <v-col class="pa-0">
          <v-card
            v-for="place in places"
            :key="place.id"
            outlined
          >
            <v-list-item class="pe-10 pl-10">
              <v-list-item-content>
                <v-list-item-title>
                  <v-row>
                    <v-col
                      cols="10"
                      justify="center"
                      class="pb-0 pt-0"
                    >
                      <a
                        :href="place.locationDto.placeUrl"
                        style="text-decoration: none;"
                      >
                        {{place.locationDto.placeName}}
                      </a>
                    </v-col>
                    <v-spacer/>
                    <v-col
                      cols="auto"
                      class="pa-0"
                    >
                      <v-icon
                        :data-id="place.id"
                        @click="deleteLocation"
                        size="30"
                      >
                        mdi-minus
                      </v-icon>
                    </v-col>
                  </v-row>
                </v-list-item-title>

                <v-list-item-subtitle>
                  {{place.locationDto.phone}}
                </v-list-item-subtitle>

              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-col>
      </v-row>
    </v-menu>

    <v-dialog v-model="mapDialog">
      <v-card :height="dialogHeight">
        <Map
          :studyId="studyId"
          @closeMap="closeMap"
          style="height: 100%"
        />
      </v-card>
    </v-dialog>

  </div>
</template>

<script>
  import Map from "./Map";
  import {eventBus} from "../../main";

  export default {
    name: `LocationHistoryBtn`,
    components: {Map},
    data() {
      return {
        places: null,
        keyword: ``,
        dialog: false,
        dialogUrl: null,
        mapDialog: false,
        dialogHeight: window.screen.height * 80 + `px`,
      }
    },
    props: {
      studyId: String,
    },
    methods: {
      getPlaces() {
        this.$request.get(`${window.location.origin}/api/studies/${this.studyId}/locations`,
          (error, response, body) => {
            if (response.statusCode === 200) {
              this.places = JSON.parse(body);
              window.console.log(this.places[0].locationDto);
            } else {
              window.confirm("위치 정보를 불러오는데 실패했습니다.");
            }
          });
      },
      closeMap() {
        this.mapDialog = false;
      },
      deleteLocation(event) {
        const id = event.target.getAttribute(`data-id`);
        this.$request.delete(
          `${window.location.origin}/api/locations/${id}`,
          (error, response) => {
            if (response.statusCode === 200) {
              eventBus.$emit(`raiseNotice`, `삭제 성공`);
            } else {
              eventBus.$emit(`raiseNotice`, `삭제 실패`);
            }
          });
      }
    },
  }
</script>
