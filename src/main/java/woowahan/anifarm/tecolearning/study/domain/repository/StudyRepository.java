package woowahan.anifarm.tecolearning.study.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowahan.anifarm.tecolearning.study.domain.Study;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
}
