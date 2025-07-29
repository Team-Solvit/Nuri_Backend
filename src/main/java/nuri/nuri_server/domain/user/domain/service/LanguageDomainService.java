package nuri.nuri_server.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.Language;
import nuri.nuri_server.domain.user.domain.exception.LanguageNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageDomainService {
    private final LanguageRepository languageRepository;

    public Language getLanguageByName(String languageName) {
        return languageRepository.findByName(languageName).orElseThrow(() -> new LanguageNotFoundException(languageName));
    }
}
