package nuri.nuri_server.domain.user.domain.repository;

import nuri.nuri_server.domain.user.domain.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    List<Language> findByNameIn(List<String> languages);
}
