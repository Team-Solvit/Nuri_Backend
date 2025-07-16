package nuri.nuri_server.domain.country.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.country.domain.exception.CountryNotFoundException;
import nuri.nuri_server.domain.country.domain.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryEntity getCountryEntity(String countryName) {
        return countryRepository.findByName(countryName)
                .orElseThrow(() -> new CountryNotFoundException(countryName));
    }
}
