package woowahan.anifarm.tecolearning.study.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.domain.UserRepository;
import woowahan.anifarm.tecolearning.user.exception.UserNotFoundException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.by;

class StudyRepositoryTest extends AbstractWebTestClient {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private UserRepository userRepository;

    private User presenter;

    @BeforeEach
    void setUp() {
        // TODO: 2019-12-14 ID 관리하는 테스트 객체 필요할 듯.
        presenter = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        studyRepository.deleteAll();
    }

    @Test
    @DisplayName("스터디를 page단위로 가져온다.")
    void findStudyPage() {
        // Given
        int offset = 0;
        int pageSize = 10;
        int totalNumberOfStudy = 30;
        Sort sort = by(Sort.Direction.ASC, "createdDate");

        saveStudies(totalNumberOfStudy);
        PageRequest pageRequest = PageRequest.of(offset, pageSize, sort);

        // When
        Page<Study> pageOfStudy = studyRepository.findAll(pageRequest);

        // Then
        assertThat(pageOfStudy.getContent().size()).isEqualTo(pageSize);
    }

    @Test
    @DisplayName("스터디의 개수가 pageSize보다 작은 경우, 저장되어있는 스터디를 모두 가져온다.")
    void findStudy_ifNumberOfSavedStudy_isSmallerThanPageSize() {
        // Given
        int offSet = 0;
        int pageSize = 10;
        int numberOfNewStudy = 5;
        PageRequest pageRequest = PageRequest.of(offSet, pageSize);

        saveStudies(numberOfNewStudy);

        // When
        Page<Study> pageOfStudy = studyRepository.findAll(pageRequest);

        // Then
        assertThat(pageOfStudy.getContent().size()).isEqualTo(numberOfNewStudy);
    }

    private void saveStudies(int totalNumberOfStudy) {
        for (int i = 0; i < totalNumberOfStudy; i++) {
            Study study = Study.builder()
                    .subject("subject")
                    .presenter(presenter)
                    .totalNumberOfRecruitment(6)
                    .startDate(LocalDate.of(2019, 12, 14))
                    .location("송파구")
                    .endDate(LocalDate.of(2019, 12, 15))
                    .description("description")
                    .status(StudyStatus.RECRUITING)
                    .build();
            studyRepository.save(study);
        }
    }

}