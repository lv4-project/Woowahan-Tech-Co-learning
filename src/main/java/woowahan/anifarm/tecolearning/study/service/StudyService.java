package woowahan.anifarm.tecolearning.study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.study.service.exception.StudyNotFoundException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

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
