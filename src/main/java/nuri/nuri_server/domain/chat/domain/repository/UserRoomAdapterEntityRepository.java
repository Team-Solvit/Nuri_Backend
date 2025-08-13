package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.UserRoomAdapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoomAdapterEntityRepository extends JpaRepository<UserRoomAdapterEntity, UUID> {
    @Query("select a.user.id from UserRoomAdapterEntity a where a.room.id = :roomId")
    List<String> findUsersByRoomId(UUID roomId);
}
