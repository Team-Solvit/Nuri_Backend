package nuri.nuri_server.domain.user.domain.repository;

import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAgreementRepository extends JpaRepository<UserAgreementEntity, UUID> {
}
