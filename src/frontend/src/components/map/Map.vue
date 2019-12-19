<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <div
          id="map"
          style="width: 100vw; height: 100vw">
        </div>
      </v-col>

      <v-col cols="12">
        <v-btn
          color="success"
          class="mr-4"
          type="submit"
          @click="sendSelectedPlace"
        >
          확인
        </v-btn>
      </v-col>

      <MapCard
        :ps="ps"
        @updateMap="updateMap"
        @updateMarkers="updateMarkers"
      />

    </v-row>
  </v-container>
</template>

<script>
  import MapCard from "./MapCard";

  export default {
    name: `dmap`,
    components: {MapCard},
    data() {
      return {
        msg: `다음맵 테스트`,
        places: [],
        ps: {},
        markers: [],
        infowindow: {},
        map: {},
        selectedPlace: {},
      }
    },
    methods: {
      updateMap(places) {
        this.displayPlaces(places)
      },
      displayPlaces(places) {
        const kakao = window.kakao;
        const bounds = new kakao.maps.LatLngBounds();
        // 지도에 표시되고 있는 마커를 제거합니다
        this.removeMarker();

        for (let i = 0; i < places.length; i++) {
          // 마커를 생성하고 지도에 표시합니다
          const placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
          this.addMarker(placePosition, i);
          // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
          // LatLngBounds 객체에 좌표를 추가합니다
          bounds.extend(placePosition);
        }
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        this.map.setBounds(bounds);
      },
      removeMarker() {
        for (let i = 0; i < this.markers.length; i++) {
          this.markers[i].setMap(null);
        }
        this.markers = [];
      },
      addMarker(position, idx) {
        const kakao = window.kakao;

        const imageSrc = `http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png`, // 마커 이미지 url, 스프라이트 이미지를 씁니다
          imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
          imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
          },
          markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
          marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
          });

        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.markers.push(marker);  // 배열에 생성된 마커를 추가합니다
        return marker;
      },
      updateMarkers(position, place) {
        const kakao = window.kakao;

        const bounds = new kakao.maps.LatLngBounds();
        const placePosition = new kakao.maps.LatLng(place.y, place.x);
        bounds.extend(placePosition);
        this.map.setBounds(bounds);
        this.selectedPlace = place;
        this.displayInfowindow(this.markers[position], place.place_name)
      },
      displayInfowindow(marker, title) {
        const content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

        this.infowindow.setContent(content);
        this.infowindow.open(this.map, marker);
      },
      sendSelectedPlace() {
        window.console.log(this.selectedPlace);
        this.$emit(`sendSelectedPlace`, this.selectedPlace);
        window.history.back();
      }
    },
    mounted() {
      const kakao = window.kakao;
      const app = this;

      const mapContainer = document.getElementById(`map`), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
          level: 3 // 지도의 확대 레벨
        };

      app.map = new kakao.maps.Map(mapContainer, mapOption);
      app.ps = new kakao.maps.services.Places();
      app.infowindow = new kakao.maps.InfoWindow({zIndex: 1});
    },
  }
</script>