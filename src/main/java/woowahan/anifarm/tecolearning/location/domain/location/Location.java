package woowahan.anifarm.tecolearning.location.domain.location;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name = "location")
public class Location {

    @Id
    @Column(name = "location_id", nullable = false)
    private Long id;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "place_url")
    private String placeUrl;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

    @Builder
    public Location(Long id, String placeName, String phone, String placeUrl, String x, String y) {
        this.id = id;
        this.placeName = placeName;
        this.phone = phone;
        this.placeUrl = placeUrl;
        this.x = x;
        this.y = y;
    }
}
