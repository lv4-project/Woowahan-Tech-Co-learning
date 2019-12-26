package woowahan.anifarm.tecolearning.studyoutput.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowahan.anifarm.tecolearning.studyoutput.domain.StudyOutput;

@Repository
public interface StudyOutputRepository extends JpaRepository<StudyOutput, Long> {
}
