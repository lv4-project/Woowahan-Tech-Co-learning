package woowahan.anifarm.tecolearning.location.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.location.service.StudyLocationService;
import woowahan.anifarm.tecolearning.location.service.dto.LocationDto;
import woowahan.anifarm.tecolearning.location.service.dto.StudyLocationDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudyLocationController {
    private final StudyLocationService studyLocationService;

    public StudyLocationController(StudyLocationService studyLocationService) {
        this.studyLocationService = studyLocationService;
    }

    @GetMapping("/studies/{studyId}/locations")
    public ResponseEntity<List<StudyLocationDto>> read(@PathVariable long studyId) {
        return ResponseEntity.ok(studyLocationService.read(studyId));
    }

    @PostMapping("/studies/{studyId}/locations")
    public ResponseEntity<LocationDto> save(@PathVariable long studyId, @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(studyLocationService.save(studyId, locationDto));
    }

    @DeleteMapping("/locations/{studyLocationId}")
    public ResponseEntity delete(@PathVariable long studyLocationId) {
        studyLocationService.delete(studyLocationId);

        return ResponseEntity.ok().build();
    }
}
