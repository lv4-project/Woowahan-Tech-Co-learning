package woowahan.anifarm.tecolearning.map.dto;

import lombok.*;
import woowahan.anifarm.tecolearning.map.domain.StudyLocation;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class StudyLocationDto {
    private long id;
    private LocationDto locationDto;

    @Builder
    public StudyLocationDto(long id, LocationDto locationDto) {
        this.id = id;
        this.locationDto = locationDto;
    }

    public static StudyLocationDto from(StudyLocation studyLocation) {
        return StudyLocationDto.builder()
                .id(studyLocation.getId())
                .locationDto(LocationDto.from(studyLocation.getLocation()))
                .build();
    }
}
