package woowahan.anifarm.tecolearning.map.service;

import org.springframework.stereotype.Service;
import woowahan.anifarm.tecolearning.map.domain.Location;
import woowahan.anifarm.tecolearning.map.domain.LocationRepository;
import woowahan.anifarm.tecolearning.map.dto.LocationDto;

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
