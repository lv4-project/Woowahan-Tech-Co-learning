package woowahan.anifarm.tecolearning.study.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.study.service.StudyService;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;

import java.util.List;

@RestController
@RequestMapping("/api/studies/summary")
public class StudySummaryApiController {
    private final StudyService studyService;

    public StudySummaryApiController(StudyService studyService) {
        this.studyService = studyService;
    }

    // TODO: 2019-12-19 리펙토링중
    @GetMapping
    public ResponseEntity<List<StudySummaryDto>> readOnePage(Pageable pageable) {
        return ResponseEntity.ok(studyService.findPageOfSummaryDto(pageable));
    }

    @GetMapping("/participating")
    public ResponseEntity<List<StudySummaryDto>> readOnePageOfParticipating(Pageable pageable) {
        return ResponseEntity.ok(studyService.findPageOfSummaryDto(StudyStatus.RECRUITING, pageable));
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<StudySummaryDto>> readOnePageOfOngoing(Pageable pageable) {
        return ResponseEntity.ok(studyService.findPageOfSummaryDto(StudyStatus.ONGOING, pageable));
    }

    @GetMapping("/finished")
    public ResponseEntity<List<StudySummaryDto>> readOnePageOfFinished(Pageable pageable) {
        return ResponseEntity.ok(studyService.findPageOfSummaryDto(StudyStatus.FINISHED, pageable));
    }
}
