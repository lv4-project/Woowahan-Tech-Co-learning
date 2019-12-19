package woowahan.anifarm.tecolearning.study.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyParticipantRepository;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyUpdateDto;
import woowahan.anifarm.tecolearning.study.service.exception.InvalidParticipatingRequestException;
import woowahan.anifarm.tecolearning.study.service.exception.StudyNotFoundException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static woowahan.anifarm.tecolearning.study.domain.StudyParticipantStatus.*;

@Service
public class StudyService {
    private final UserService userService;
    private final StudyRepository studyRepository;
    private final StudyParticipantRepository studyParticipantRepository;

    public StudyService(UserService userService,
                        StudyRepository studyRepository,
                        StudyParticipantRepository studyParticipantRepository) {
        this.userService = userService;
        this.studyRepository = studyRepository;
        this.studyParticipantRepository = studyParticipantRepository;
    }

    public StudyInfoDto save(StudyCreateDto studyCreateDto, UserInfoDto userInfoDto) {
        User user = userService.findById(userInfoDto.getId());

        Study study = studyCreateDto.toEntity(user);
        Study saved = studyRepository.save(study);

        StudyParticipant studyParticipant = StudyParticipant.builder()
                .participant(user)
                .study(saved)
                .build();

        studyParticipantRepository.save(studyParticipant);

        return StudyInfoDto.of(saved, PRESENTER.getStatus());
    }

    public Study findById(long studyId) {
        return studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
    }

    public StudyInfoDto findInfoDtoById(long studyId, UserInfoDto userInfoDto) {
        Study study = findById(studyId);
        User presenter = study.getPresenter();
        User loggedInUser = userService.findById(userInfoDto.getId());

        if (presenter.isSame(loggedInUser)) {
            return StudyInfoDto.of(study, PRESENTER.getStatus());
        }

        if (studyParticipantRepository.existsByStudyAndParticipant(study, loggedInUser)) {
            return StudyInfoDto.of(study, PARTICIPANT.getStatus());
        }

        return StudyInfoDto.of(study, NON_PARTICIPANT.getStatus());
    }

    public List<StudySummaryDto> findPageOfSummaryDto(Pageable pageable) {
        Page<Study> pageOfStudy = studyRepository.findAll(pageable);

        List<StudySummaryDto> studInfoDtos = pageOfStudy.stream()
                .map(StudySummaryDto::from)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(studInfoDtos);
    }

    @Transactional
    public StudyInfoDto update(long studyId,
                               StudyUpdateDto studyUpdateDto,
                               UserInfoDto loginUser) {
        Study oldStudy = findById(studyId);
        Study newStudy = studyUpdateDto.toEntity();

        authenticate(oldStudy.getPresenter(), loginUser.getId());
        return StudyInfoDto.of(oldStudy.update(newStudy), PRESENTER.getStatus());
    }

    private void authenticate(User oldPresenter, long userId) {
        User presenter = userService.findById(userId);
        presenter.authenticate(oldPresenter);
    }

    public String participateInStudy(long studyId, UserInfoDto userInfoDto) {
        Study participatingStudy = findById(studyId);
        User user = userService.findById(userInfoDto.getId());

        checkParticipating(participatingStudy, user);
        StudyParticipant studyParticipant = StudyParticipant.builder()
                .study(participatingStudy)
                .participant(user)
                .build();

        studyParticipantRepository.save(studyParticipant);

        return PARTICIPANT.getStatus();
    }

    private void checkParticipating(Study participatingStudy, User user) {
        if (studyParticipantRepository.existsByStudyAndParticipant(participatingStudy, user)) {
            throw new InvalidParticipatingRequestException();
        }
    }
}
