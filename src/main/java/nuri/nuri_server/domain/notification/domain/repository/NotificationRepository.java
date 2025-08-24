package nuri.nuri_server.domain.notification.domain.repository;

import nuri.nuri_server.domain.notification.domain.entity.NotificationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    Long countAllByUserId(UUID userId);
    List<NotificationEntity> findAllByUserId(UUID id, Pageable pageable);
}
