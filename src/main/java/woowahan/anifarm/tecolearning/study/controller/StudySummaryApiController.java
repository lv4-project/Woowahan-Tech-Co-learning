package woowahan.anifarm.tecolearning.study.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public ResponseEntity<List<StudySummaryDto>> readOnePage(Pageable pageable) {
        return ResponseEntity.ok(studyService.findPageOfSummaryDto(pageable));
    }
}
