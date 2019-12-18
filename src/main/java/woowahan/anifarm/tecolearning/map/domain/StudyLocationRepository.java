package woowahan.anifarm.tecolearning.map.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyLocationRepository extends JpaRepository<StudyLocation, Long> {

    List<StudyLocation> findAllByStudyId(long studyId);
}
