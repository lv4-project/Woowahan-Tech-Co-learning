package woowahan.anifarm.tecolearning.studyoutput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.auth.advice.LoggedInUser;
import woowahan.anifarm.tecolearning.studyoutput.service.StudyOutputService;
import woowahan.anifarm.tecolearning.studyoutput.service.dto.StudyOutputDto;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.List;

@RestController
public class StudyOutputApiController {
    private final StudyOutputService studyOutputService;

    public StudyOutputApiController(StudyOutputService studyOutputService) {
        this.studyOutputService = studyOutputService;
    }

    @PostMapping("/api/studies/{studyId}/outputs")
    public ResponseEntity<StudyOutputDto> create(@PathVariable long studyId,
                                                 @RequestBody StudyOutputDto studyOutputDto,
                                                 @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyOutputService.save(studyId, studyOutputDto, userInfoDto));
    }

    @GetMapping("/api/outputs/{outputId}")
    public ResponseEntity<StudyOutputDto> read(@PathVariable long outputId) {
        return ResponseEntity.ok(studyOutputService.findByInfoDto(outputId));
    }

    @PutMapping("/api/outputs/{outputId}")
    public ResponseEntity<StudyOutputDto> update(@PathVariable long outputId,
                                                 @RequestBody StudyOutputDto studyOutputDto,
                                                 @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyOutputService.update(outputId, studyOutputDto, userInfoDto));
    }

    @GetMapping("/api/studies/{studyId}/outputs")
    public ResponseEntity<List<StudyOutputDto>> readAll(@PathVariable long studyId) {
        return ResponseEntity.ok(studyOutputService.findAllByInfoDto(studyId));
    }

    @DeleteMapping("/api/outputs/{outputId}")
    public ResponseEntity<?> delete(@PathVariable long outputId,
                                    @LoggedInUser UserInfoDto userInfoDto) {
        studyOutputService.delete(outputId, userInfoDto);
        return ResponseEntity.ok().build();
    }
}
