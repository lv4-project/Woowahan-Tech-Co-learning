package woowahan.anifarm.tecolearning.map.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.map.dto.LocationDto;
import woowahan.anifarm.tecolearning.map.dto.StudyLocationDto;
import woowahan.anifarm.tecolearning.map.service.StudyLocationService;

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
