package woowahan.anifarm.tecolearning.study.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipantStatus;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.study.service.dto.*;
import woowahan.anifarm.tecolearning.study.service.exception.StudyNotFoundException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static woowahan.anifarm.tecolearning.study.domain.StudyParticipantStatus.*;

@Service
public class StudyService {
    private final UserService userService;
    private final StudyRepository studyRepository;
    private final StudyParticipantService studyParticipantService;

    public StudyService(UserService userService,
                        StudyRepository studyRepository,
                        StudyParticipantService studyParticipantService) {
        this.userService = userService;
        this.studyRepository = studyRepository;
        this.studyParticipantService = studyParticipantService;
    }

    public StudyInfoDto save(StudyCreateDto studyCreateDto, UserInfoDto userInfoDto) {
        User user = userService.findById(userInfoDto.getId());

        Study study = studyCreateDto.toEntity(user);
        Study saved = studyRepository.save(study);

        StudyParticipant studyParticipant = StudyParticipant.builder()
                .participant(user)
                .study(saved)
                .build();

        studyParticipantService.save(studyParticipant);

        return StudyInfoDto.of(saved, PRESENTER.getStatus());
    }

    public Study findById(long studyId) {
        return studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
    }

    public StudyDetailInfoDto findInfoDtoById(long studyId, UserInfoDto userInfoDto) {
        Study study = findById(studyId);
        User presenter = study.getPresenter();
        User loggedInUser = userService.findById(userInfoDto.getId());

        if (loggedInUser.is(presenter)) {
            return createStudyDetailInfo(study, PRESENTER);
        }

        if (studyParticipantService.existsByStudyAndParticipant(study, loggedInUser)) {
            return createStudyDetailInfo(study, PARTICIPANT);
        }
        // TODO: 2019-12-19  return StudyDetailInfoDto.from(findById(studyId)); 로 변경
        return createStudyDetailInfo(study, NON_PARTICIPANT);
    }

    private StudyDetailInfoDto createStudyDetailInfo(Study study,
                                                     StudyParticipantStatus studyParticipantStatus) {
        Set<UserInfoDto> participants = studyParticipantService.findAllParticipantsOf(study);

        return StudyDetailInfoDto.of(
                study,
                studyParticipantStatus.getStatus(),
                participants.size(),
                participants
        );
    }

    public List<StudySummaryDto> findPageOfSummaryDto(String statusName, Pageable pageable) {
        StudyStatus status = StudyStatus.of(statusName);
        Page<Study> pageOfStudy = studyRepository.findAllByStatus(status, pageable);

        List<StudySummaryDto> studInfoDtos = pageOfStudy.stream()
                .map(this::createStudySummaryDtoFrom)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(studInfoDtos);
    }

    private StudySummaryDto createStudySummaryDtoFrom(Study study) {
        long studyId = study.getId();
        int numberOfParticipants = studyParticipantService.countOfParticipant(studyId);

        return StudySummaryDto.from(study, numberOfParticipants);
    }

    @Transactional
    public StudyInfoDto update(long studyId,
                               StudyUpdateDto studyUpdateDto,
                               UserInfoDto loginUser) {
        Study oldStudy = findById(studyId);
        Study newStudy = studyUpdateDto.toEntity();

        authenticate(oldStudy.getPresenter(), loginUser.getId());
        Study updated = oldStudy.update(newStudy);
        return StudyInfoDto.of(updated, PRESENTER.getStatus());
    }

    private void authenticate(User oldPresenter, long userId) {
        User presenter = userService.findById(userId);
        presenter.authenticate(oldPresenter);
    }

    // TODO: 2019-12-24 StudyDetailInfo를 되돌려주는건 어떨까?
    public String participateInStudy(long studyId, UserInfoDto userInfoDto) {
        Study participatingStudy = findById(studyId);
        User user = userService.findById(userInfoDto.getId());

        StudyParticipant studyParticipant = StudyParticipant.builder()
                .study(participatingStudy)
                .participant(user)
                .build();

        studyParticipantService.save(studyParticipant);

        return PARTICIPANT.getStatus();
    }

    @Transactional
    public StudyDetailInfoDto startStudy(long studyId, UserInfoDto userInfoDto) {
        Study study = findById(studyId);

        Study started = study.start(userInfoDto.getId());

        return createStudyDetailInfo(started, PRESENTER);
    }

    @Transactional
    public StudyDetailInfoDto endStudy(long studyId, UserInfoDto userInfoDto) {
        Study study = findById(studyId);

        Study finished = study.finish(userInfoDto.getId());

        return createStudyDetailInfo(finished, PRESENTER);
    }

    public void delete(long studyId, UserInfoDto userInfoDto) {
        Study study = findById(studyId);

        study.checkNotPresenter(userInfoDto.getId());
        studyRepository.delete(study);
        studyParticipantService.deleteByStudy(study);
    }

    public List<StudyInfoDto> findInfoByUserId(long userId) {
        // TODO: 2019-12-24 service test 구현
        User user = userService.findById(userId);
        return studyParticipantService
                .findByUser(user).stream()
                .map(StudyParticipant::getStudy)
                .map(StudyInfoDto::of)
                .collect(Collectors.toList());
    }

    public List<StudySummaryDto> search(String status, String keyword, Pageable pageable) {
        StudyStatus studyStatus = StudyStatus.of(status);
        return studyRepository
                .findAllByStatusAndSubjectIgnoreCaseContainingOrderByCreatedDate(studyStatus, keyword, pageable)
                .map(this::createStudySummaryDtoFrom)
                .getContent();
    }
}
