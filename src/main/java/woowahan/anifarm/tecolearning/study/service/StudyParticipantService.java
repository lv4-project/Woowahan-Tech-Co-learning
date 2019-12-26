package woowahan.anifarm.tecolearning.study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyParticipantRepository;
import woowahan.anifarm.tecolearning.study.service.dto.StudyParticipantInfoDto;
import woowahan.anifarm.tecolearning.study.service.exception.StudyParticipantNotFoundException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            throw new StudyParticipantNotFoundException();
        }
    }

    public Set<UserInfoDto> findAllParticipantsOf(Study study) {
        Set<StudyParticipant> studyParticipants = studyParticipantRepository.findAllByStudy(study);

        return studyParticipants.stream()
                .map(StudyParticipant::getParticipant)
                .map(UserInfoDto::from)
                .collect(Collectors.toSet());
    }

    public int countOfParticipant(long studyId) {
        return studyParticipantRepository.countByStudyId(studyId);
    }

    @Transactional
    public void deleteByStudy(Study study) {
        studyParticipantRepository.deleteByStudy(study);
    }

    public List<StudyParticipant> findByUser(User user) {
        return studyParticipantRepository.findAllByParticipant(user);
    }

    @Transactional
    public void withdrawStudy(long studyId, UserInfoDto userInfoDto) {
        long userId = userInfoDto.getId();

        studyParticipantRepository.findByStudyIdAndParticipantId(studyId, userId)
                .orElseThrow(StudyParticipantNotFoundException::new)
                .checkPresenter(userId);

        studyParticipantRepository.deleteByStudyIdAndParticipantId(studyId, userId);
    }
}
