package woowahan.anifarm.tecolearning.study.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.user.domain.User;

import java.util.Set;

public interface StudyParticipantRepository extends JpaRepository<StudyParticipant, Long> {
    boolean existsByStudyAndParticipant(Study study, User user);

    void deleteByStudy(Study study);

    int countByStudyId(long studyId);

    Set<StudyParticipant> findAllByStudy(Study study);
}
