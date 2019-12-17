package woowahan.anifarm.tecolearning.studyoutput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.service.StudyService;
import woowahan.anifarm.tecolearning.studyoutput.domain.StudyOutput;
import woowahan.anifarm.tecolearning.studyoutput.domain.repository.StudyOutputRepository;
import woowahan.anifarm.tecolearning.studyoutput.service.dto.StudyOutputDto;
import woowahan.anifarm.tecolearning.studyoutput.service.exception.OutputNotFoundException;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class StudyOutputService {
    private final StudyOutputRepository studyOutputRepository;
    private final StudyService studyService;

    public StudyOutputService(StudyOutputRepository studyOutputRepository, StudyService studyService) {
        this.studyOutputRepository = studyOutputRepository;
        this.studyService = studyService;
    }

    public StudyOutputDto save(long studyId, StudyOutputDto studyOutputDto, UserInfoDto userInfoDto) {
        Study study = studyService.findById(studyId);
        // TODO: 2019-12-16 그룹내 인원이면, 작성 가능하도록 변경
        study.checkPermission(userInfoDto.getId());

        StudyOutput studyOutput = studyOutputDto.toEntity(study);
        return StudyOutputDto.from(studyOutputRepository.save(studyOutput));
    }

    public StudyOutputDto findByInfoDto(long outputId) {
        return StudyOutputDto.from(findById(outputId));
    }

    private StudyOutput findById(long outputId) {
        return studyOutputRepository.findById(outputId).orElseThrow(OutputNotFoundException::new);
    }

    @Transactional
    public StudyOutputDto update(long outputId, StudyOutputDto studyOutputDto, UserInfoDto userInfoDto) {
        StudyOutput oldStudyOutput = findById(outputId);
        StudyOutput newStudyOutput = studyOutputDto.toEntity();

        oldStudyOutput.getStudy().checkPermission(userInfoDto.getId());

        return StudyOutputDto.from(oldStudyOutput.update(newStudyOutput));
    }

    public List<StudyOutputDto> findAllByInfoDto(long studyId) {
        return studyService.findById(studyId)
                .getStudyOutputs().stream()
                .map(StudyOutputDto::from)
                .collect(toList());
    }

    public void delete(long outputId) {
        studyOutputRepository.delete(findById(outputId));
    }
}
