<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-form
          v-on:submit.prevent="searchPlaces()"
        >
          <v-container>
            <v-row>
              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="keyword"
                  label="키워드"
                  required
                >
                </v-text-field>
              </v-col>

              <v-btn
                color="success"
                class="mr-4"
                type="submit"
              >
                검색
              </v-btn>

            </v-row>
          </v-container>
        </v-form>

        <v-card
          v-for="place in places"
          :key="place.id"
          class="mx-auto mb-5"
          outlined
          style="padding: 0"
          @click="changeMarkers"
        >
          <div :data-id="place.id">
            <div>
              {{place.place_name}}
            </div>

            <div>
              {{place.address_name}}
            </div>

            <div>
              {{place.phone}}
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

  export default {
    name: `MapCard`,
    data() {
      return {
        places: [],
        keyword: ``,
      }
    },
    props: {
      ps: Object,
    },
    methods: {
      searchPlaces() {
        const app = this;
        const kakao = window.kakao;

        if (!this.keyword.replace(/^\s+|\s+$/g, ``)) {
          window.alert(`키워드를 입력해주세요!`);
          return false;
        }

        function placesSearchCB(data, status, pagination) {
          if (status === kakao.maps.services.Status.OK) {
            app.places = data;

            app.displayPagination(pagination);

            app.changeMap(app.places);
          } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            window.alert('검색 결과가 존재하지 않습니다.');
            return;
          } else if (status === kakao.maps.services.Status.ERROR) {
            window.alert('검색 결과 중 오류가 발생했습니다.');
            return;
          }
        }

        // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
        app.ps.keywordSearch(app.keyword, placesSearchCB);
      },
      displayPagination(pagination) {
        // TODO: 페이지 이동
        //pagination.gotoPage(1);
        pagination
      },
      changeMap(places) {
        this.$emit(`updateMap`, places);
      },
      changeMarkers(event) {
        const id = event.target.parentNode.getAttribute(`data-id`);
        const place = this.places.filter(place => place.id == id)[0];

        const index = this.places.indexOf(place);

        this.$emit(`updateMarkers`, index, place);
      },
    },
  }
</script>
