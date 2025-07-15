package nuri.nuri_server.domain.user.domain.repository;

import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByUsername(String username);
}
