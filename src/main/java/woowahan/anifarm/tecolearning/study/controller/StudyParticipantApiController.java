package woowahan.anifarm.tecolearning.study.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.auth.advice.LoggedInUser;
import woowahan.anifarm.tecolearning.study.service.StudyParticipantService;
import woowahan.anifarm.tecolearning.study.service.StudyService;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

@RestController
@RequestMapping("/api/studies/{studyId}/participants")
public class StudyParticipantApiController {
    private final StudyService studyService;
    private final StudyParticipantService studyParticipantService;

    public StudyParticipantApiController(StudyService studyService,
                                         StudyParticipantService studyParticipantService) {
        this.studyService = studyService;
        this.studyParticipantService = studyParticipantService;
    }

    @PostMapping
    public ResponseEntity<String> participateInStudy(@PathVariable long studyId,
                                                     @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok(studyService.participateInStudy(studyId, userInfoDto));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> countOfParticipant(@PathVariable long studyId) {
        return ResponseEntity.ok().body(studyParticipantService.countOfParticipant(studyId));
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@PathVariable long studyId,
                                         @LoggedInUser UserInfoDto userInfoDto) {
        studyParticipantService.withdrawStudy(studyId, userInfoDto);

        return ResponseEntity.ok().build();
    }
}
