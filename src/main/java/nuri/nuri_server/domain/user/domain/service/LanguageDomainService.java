package nuri.nuri_server.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.Language;
import nuri.nuri_server.domain.user.domain.exception.LanguageNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageDomainService {
    private final LanguageRepository languageRepository;

    public List<Language> findAllByLanguage(List<String> languages) {
        return languages.stream().map(
                (language) -> languageRepository.findByName(language).orElseThrow(() -> new LanguageNotFoundException(language))
        ).toList();
    }
}
