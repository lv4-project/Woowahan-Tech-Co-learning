package woowahan.anifarm.tecolearning.study.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    Page<Study> findAllByStatus(StudyStatus status, Pageable pageable);

    Page<Study> findAllByStatusAndSubjectIgnoreCaseContainingOrderByCreatedDate(StudyStatus status, String subject, Pageable pageable);
}
