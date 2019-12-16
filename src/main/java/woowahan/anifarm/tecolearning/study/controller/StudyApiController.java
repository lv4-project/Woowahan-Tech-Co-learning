package woowahan.anifarm.tecolearning.study.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.study.service.StudyService;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyUpdateDto;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.List;

@RestController
@RequestMapping("/api/studies")
public class StudyApiController {
    private final StudyService studyService;

    public StudyApiController(StudyService studyService) {
        this.studyService = studyService;
    }

    @PostMapping
    public ResponseEntity<StudyInfoDto> create(@RequestBody StudyCreateDto studyCreateDto) {
        UserInfoDto userInfoDto = UserInfoDto.builder().id(1L).build();
        return ResponseEntity.ok(studyService.save(studyCreateDto, userInfoDto));
    }

    @GetMapping("/{studyId}")
    public ResponseEntity<StudyInfoDto> read(@PathVariable long studyId) {
        return ResponseEntity.ok(studyService.findInfoDtoById(studyId));
    }

    @PutMapping("/{studyId}")
    public ResponseEntity<StudyInfoDto> update(@PathVariable long studyId,
                                               @RequestBody StudyUpdateDto studyUpdateDto) {
        // TODO: 2019-12-12 User 받아서 쓰기
        UserInfoDto userInfoDto = UserInfoDto.builder().id(1L).build();
        return ResponseEntity.ok(studyService.update(studyId, studyUpdateDto, userInfoDto));
    }
}