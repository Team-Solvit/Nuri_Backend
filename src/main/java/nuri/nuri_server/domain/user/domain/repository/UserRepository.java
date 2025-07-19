package nuri.nuri_server.domain.user.domain.repository;

import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByUserId(String userId);

    Optional<UserEntity> findByUserId(String userId);

    @Query("select u.role from UserEntity u where u.userId = :userId")
    Optional<Role> findRoleByUserId(String userId);
}
