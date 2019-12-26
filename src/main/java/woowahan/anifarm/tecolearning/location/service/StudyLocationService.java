package woowahan.anifarm.tecolearning.location.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowahan.anifarm.tecolearning.location.domain.studylocation.StudyLocation;
import woowahan.anifarm.tecolearning.location.domain.studylocation.StudyLocationRepository;
import woowahan.anifarm.tecolearning.location.service.dto.LocationDto;
import woowahan.anifarm.tecolearning.location.service.dto.StudyLocationDto;
import woowahan.anifarm.tecolearning.study.service.StudyService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudyLocationService {
    private final StudyService studyService;
    private final LocationService locationService;
    private final StudyLocationRepository studyLocationRepository;

    public StudyLocationService(StudyService studyService, LocationService locationService, StudyLocationRepository studyLocationRepository) {
        this.studyService = studyService;
        this.locationService = locationService;
        this.studyLocationRepository = studyLocationRepository;
    }

    public LocationDto save(long studyId, LocationDto locationDto) {
        StudyLocation studyLocation = StudyLocation.builder()
                .study(studyService.findById(studyId))
                .location(locationService.getOrCreate(locationDto))
                .build();

        return LocationDto.from(studyLocationRepository.save(studyLocation)
                .getLocation());
    }

    public void delete(long studyLocationId) {
        studyLocationRepository.deleteById(studyLocationId);
    }

    public List<StudyLocationDto> read(long studyId) {
        return studyLocationRepository.findAllByStudyId(studyId).stream()
                .map(StudyLocationDto::from)
                .collect(Collectors.toList());
    }
}
