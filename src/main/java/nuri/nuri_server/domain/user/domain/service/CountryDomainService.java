package nuri.nuri_server.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.domain.exception.CountryNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryDomainService {

    private final CountryRepository countryRepository;

    public CountryEntity getCountryEntity(String countryName) {
        return countryRepository.findByName(countryName)
                .orElseThrow(() -> new CountryNotFoundException(countryName));
    }
}
