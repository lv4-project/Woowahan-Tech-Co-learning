package woowahan.anifarm.tecolearning.study.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyUpdateDto;
import woowahan.anifarm.tecolearning.study.service.exception.StudyNotFoundException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyService {
    private final UserService userService;
    private final StudyRepository studyRepository;

    public StudyService(UserService userService,
                        StudyRepository studyRepository) {
        this.userService = userService;
        this.studyRepository = studyRepository;
    }

    public StudyInfoDto save(StudyCreateDto studyCreateDto, UserInfoDto userInfoDto) {
        User user = userService.findById(userInfoDto.getId());

        Study study = studyCreateDto.toEntity(user);
        Study saved = studyRepository.save(study);
        return StudyInfoDto.from(saved);
    }

    public Study findById(long studyId) {
        return studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
    }

    public StudyInfoDto findInfoDtoById(long studyId) {
        return StudyInfoDto.from(findById(studyId));
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
        return StudyInfoDto.from(oldStudy.update(newStudy));
    }

    private void authenticate(User oldPresenter, long userId) {
        User presenter = userService.findById(userId);
        presenter.authenticate(oldPresenter);
    }
}
