package nuri.nuri_server.domain.country.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.country.domain.exception.CountryNotFoundException;
import nuri.nuri_server.domain.country.domain.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public CountryEntity getCountryEntity(String countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }
}
