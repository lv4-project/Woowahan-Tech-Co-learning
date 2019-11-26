package woowahan.anifarm.tecolearning.study;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Study {
    private static final int SUBJECT_LENGTH_UTF_8 = 200;
    private static final int PRESENTER_LENGTH_UTF_8 = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = SUBJECT_LENGTH_UTF_8)
    private String subject;

    @Column(nullable = false, length = PRESENTER_LENGTH_UTF_8)
    private String presenter;

    @Column(nullable = false)
    private Integer personnel;

    @Column(nullable = false)
    private Integer period;

    @Lob
    @Column(nullable = false)
    private String description;

}
