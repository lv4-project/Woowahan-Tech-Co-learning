<template>
  <v-container>
    <v-form
      v-on:submit.prevent="searchPlaces"
    >
      <v-text-field
        v-model="keyword"
        @click:append="searchPlaces"
        append-icon="mdi-magnify"
        label="키워드"
        required
      >
      </v-text-field>
    </v-form>
    <v-container
      id="scroll-target"
      style="max-height: 150px; padding: 0px"
      class="overflow-y-auto"
    >
      <v-card
        v-for="place in places"
        :key="place.id"
        @click="changeMarkers"
        outlined
      >
        <v-list-item>
          <v-list-item-content>
            <div :data-id="place.id">
              <v-list-item-title>
                {{place.place_name}}
              </v-list-item-title>
              <v-list-item-subtitle>
                {{place.address_name}}
              </v-list-item-subtitle>
              <v-list-item-subtitle>
                {{place.phone}}
              </v-list-item-subtitle>
            </div>
          </v-list-item-content>
        </v-list-item>

      </v-card>
    </v-container>
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
