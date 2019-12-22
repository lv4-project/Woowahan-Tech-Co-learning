package woowahan.anifarm.tecolearning.study.service;

import org.springframework.stereotype.Service;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyParticipantRepository;
import woowahan.anifarm.tecolearning.study.service.dto.StudyParticipantInfoDto;
import woowahan.anifarm.tecolearning.study.service.exception.InvalidParticipatingRequestException;
import woowahan.anifarm.tecolearning.user.domain.User;

@Service
public class StudyParticipantService {
    private final StudyParticipantRepository studyParticipantRepository;

    public StudyParticipantService(StudyParticipantRepository studyParticipantRepository) {
        this.studyParticipantRepository = studyParticipantRepository;
    }

    public boolean existsByStudyAndParticipant(Study study, User user) {
        return studyParticipantRepository.existsByStudyAndParticipant(study, user);
    }

    public StudyParticipantInfoDto save(StudyParticipant studyParticipant) {
        checkParticipating(studyParticipant);
        StudyParticipant participant = studyParticipantRepository.save(studyParticipant);

        return StudyParticipantInfoDto.from(
                participant.getParticipant().getId(),
                participant.getParticipant().getNickName(),
                participant.getParticipant().getEmail()
        );
    }

    private void checkParticipating(StudyParticipant studyParticipant) {
        if (existsByStudyAndParticipant(studyParticipant.getStudy(), studyParticipant.getParticipant())) {
            throw new InvalidParticipatingRequestException();
        }
    }

    public int countOfParticipant(long studyId) {
        return studyParticipantRepository.countByStudyId(studyId);
    }

    public void deleteByStudy(Study study) {
        studyParticipantRepository.deleteByStudy(study);
    }
}
