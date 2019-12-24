package woowahan.anifarm.tecolearning.location.domain.studylocation;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import woowahan.anifarm.tecolearning.common.BaseEntity;
import woowahan.anifarm.tecolearning.location.domain.location.Location;
import woowahan.anifarm.tecolearning.study.domain.Study;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "study_location")
public class StudyLocation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_location_id", nullable = false)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "study_id", foreignKey = @ForeignKey(name = "fk_studylocation_study"))
    private Study study;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "fk_studylocation_location"))
    private Location location;

    @Builder
    public StudyLocation(Study study, Location location) {
        this.study = study;
        this.location = location;
    }
}
