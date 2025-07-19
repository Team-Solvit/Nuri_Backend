package nuri.nuri_server.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.Language;
import nuri.nuri_server.domain.user.domain.exception.LanguageNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageDomainService {
    private final LanguageRepository languageRepository;

    public List<Language> findAllByLanguage(List<String> languages) {
        List<Language> foundLanguages = languageRepository.findByNameIn(languages);
        validateLanguagesExist(languages, foundLanguages);
        return foundLanguages;
    }

    private void validateLanguagesExist(List<String> requestedNames, List<Language> foundLanguages) {
        Set<String> foundNames = foundLanguages.stream()
                .map(Language::getName)
                .collect(Collectors.toSet());

        List<String> missing = requestedNames.stream()
                .filter(name -> !foundNames.contains(name))
                .toList();

        if (!missing.isEmpty()) {
            throw new LanguageNotFoundException(String.join(", ", missing));
        }
    }
}
