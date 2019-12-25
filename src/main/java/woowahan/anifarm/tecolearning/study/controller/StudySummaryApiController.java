package woowahan.anifarm.tecolearning.study.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.study.service.StudyService;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudySummaryApiController {
    private final StudyService studyService;

    public StudySummaryApiController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/studies/summary")
    public ResponseEntity<List<StudySummaryDto>> readOnePageOfParticipating(@RequestParam String studyStatus,
                                                                            Pageable pageable) {
        return ResponseEntity.ok(studyService.findPageOfSummaryDto(studyStatus, pageable));
    }

    @GetMapping("/users/{userId}/studies")
    // TODO: 2019-12-24 Pageable 로...
    public ResponseEntity<List<StudyInfoDto>> readAllStudyByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(studyService.findInfoByUserId(userId));
    }
}
