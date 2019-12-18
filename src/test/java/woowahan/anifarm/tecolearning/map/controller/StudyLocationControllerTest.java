package woowahan.anifarm.tecolearning.map.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.map.dto.LocationDto;

class StudyLocationControllerTest extends AbstractWebTestClient {

    @Test
    @DisplayName("스터디 장소 저장")
    void saveStudyLocation() {
        LocationDto locationDto = LocationDto.builder()
                .id(7947003)
                .placeName("석촌호수 서호")
                .phone("02-415-3595")
                .placeUrl("http://place.map.kakao.com/7947003")
                .x("127.099112837006")
                .y("37.5076807262772")
                .build();

        post("/api/studies/1/locations", locationDto)
                .expectStatus()
                .isOk()
                .expectBody(LocationDto.class)
                .isEqualTo(locationDto);
    }

    @Test
    @DisplayName("스터디 장소 삭제")
    void deleteStudyLocation() {
        delete("/api/locations/1")
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("스터디 장소 스터디 id로 조회")
    void readStudyLocation() {
        get("/api/studies/1/locations")
                .expectStatus()
                .isOk()
                .expectBodyList(LocationDto.class)
                .hasSize(1);
    }
}