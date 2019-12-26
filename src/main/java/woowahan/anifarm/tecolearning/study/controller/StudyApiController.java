package woowahan.anifarm.tecolearning.study.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.auth.advice.LoggedInUser;
import woowahan.anifarm.tecolearning.study.service.StudyService;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyDetailInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyUpdateDto;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

@RestController
@RequestMapping("/api/studies")
public class StudyApiController {
    private final StudyService studyService;

    public StudyApiController(StudyService studyService) {
        this.studyService = studyService;
    }

    @PostMapping
    public ResponseEntity<StudyInfoDto> create(@RequestBody StudyCreateDto studyCreateDto,
                                               @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyService.save(studyCreateDto, userInfoDto));
    }

    @GetMapping("/{studyId}")
    public ResponseEntity<StudyDetailInfoDto> read(@PathVariable long studyId,
                                                   @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyService.findInfoDtoById(studyId, userInfoDto));
    }

    @PutMapping("/{studyId}")
    public ResponseEntity<StudyInfoDto> update(@PathVariable long studyId,
                                               @RequestBody StudyUpdateDto studyUpdateDto,
                                               @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyService.update(studyId, studyUpdateDto, userInfoDto));
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<Void> deleteStudy(@PathVariable long studyId,
                                            @LoggedInUser UserInfoDto userInfoDto) {
        studyService.delete(studyId, userInfoDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{studyId}/start")
    public ResponseEntity<StudyDetailInfoDto> startStudy(@PathVariable long studyId,
                                                         @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyService.startStudy(studyId, userInfoDto));
    }
}
