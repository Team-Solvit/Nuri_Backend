package nuri.nuri_server.domain.user.domain.repository;

import nuri.nuri_server.domain.user.domain.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, UUID> {
    Optional<LanguageEntity> findByName(String name);
}
