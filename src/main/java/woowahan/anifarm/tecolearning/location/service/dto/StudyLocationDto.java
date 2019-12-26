package woowahan.anifarm.tecolearning.location.service.dto;

import lombok.*;
import woowahan.anifarm.tecolearning.location.domain.studylocation.StudyLocation;

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
