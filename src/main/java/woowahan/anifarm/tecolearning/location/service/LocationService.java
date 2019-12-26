package woowahan.anifarm.tecolearning.location.service;

import org.springframework.stereotype.Service;
import woowahan.anifarm.tecolearning.location.domain.location.Location;
import woowahan.anifarm.tecolearning.location.domain.location.LocationRepository;
import woowahan.anifarm.tecolearning.location.service.dto.LocationDto;

@Service
public class LocationService {
    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getOrCreate(LocationDto locationDto) {
        return locationRepository.findById(locationDto.getId())
                .orElseGet(() -> locationRepository.save(locationDto.toEntity()));
    }
}
