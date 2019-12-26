package woowahan.anifarm.tecolearning.location.service.dto;

import lombok.*;
import woowahan.anifarm.tecolearning.location.domain.location.Location;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LocationDto {
    private long id;
    private String placeName;
    private String phone;
    private String placeUrl;
    private String x;
    private String y;

    @Builder
    public LocationDto(long id, String placeName, String phone, String placeUrl, String x, String y) {
        this.id = id;
        this.placeName = placeName;
        this.phone = phone;
        this.placeUrl = placeUrl;
        this.x = x;
        this.y = y;
    }

    public static LocationDto from(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .placeName(location.getPlaceName())
                .phone(location.getPhone())
                .placeUrl(location.getPlaceUrl())
                .x(location.getX())
                .y(location.getY())
                .build();
    }

    public Location toEntity() {
        return Location.builder()
                .id(this.id)
                .placeName(this.placeName)
                .phone(this.phone)
                .placeUrl(this.placeUrl)
                .x(this.x)
                .y(this.y)
                .build();
    }
}
