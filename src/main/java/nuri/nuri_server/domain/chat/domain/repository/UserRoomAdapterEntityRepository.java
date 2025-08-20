package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.RoomEntity;
import nuri.nuri_server.domain.chat.domain.entity.UserRoomAdapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoomAdapterEntityRepository extends JpaRepository<UserRoomAdapterEntity, UUID> {
    @Query("select a.user.id from UserRoomAdapterEntity a where a.room.id = :roomId")
    List<String> findUsersByRoomId(UUID roomId);

    @Query("select a.room from UserRoomAdapterEntity a where a.user.id = :userId")
    List<RoomEntity> findRoomsByUserId(String userId);

    @Query("select a.room.id from UserRoomAdapterEntity a where a.user.id = :userId group by a.room.id having count(a.id) >= 10")
    List<UUID> findGroupRoomsByUserId(String userId);

    @Query("select not a.invitePermission from UserRoomAdapterEntity a where a.user.userId = :userId and a.room.id = :roomId")
    boolean findInvitePermissionByRoomIdAndUserId(UUID roomId, String userId);

    @Query("select count(a.user.id) from UserRoomAdapterEntity a where a.room = :room")
    Integer countByRoom(RoomEntity room);

    @Query("select count(a.user.id) from UserRoomAdapterEntity a where a.room.id = :roomId")
    Integer countByRoomId(UUID roomId);

    @Modifying
    @Query("delete from UserRoomAdapterEntity a where a.room.id = :roomId and a.user.userId = :userId")
    int deleteByRoomIdAndUserId(UUID roomId, String userId);

    @Modifying
    @Query("update UserRoomAdapterEntity a set a.lastReadAt = :now where a.user.userId = :userId and a.room.id = :roomId")
    void updateLastReadAtByRoomIdAndUserId(UUID roomId, String userId, OffsetDateTime now);

    @Query("select a from UserRoomAdapterEntity a where a.user.userId = :userId and a.room.id in :roomIds")
    List<UserRoomAdapterEntity> findByUserIdAndRoomIds(String userId, List<String> roomIds);
}
