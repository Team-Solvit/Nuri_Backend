package nuri.nuri_server.domain.chat.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

import java.time.OffsetDateTime;

@Entity
@Getter
@Table(name = "tbl_user_room_adapter")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoomAdapterEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserEntity user;

    private boolean invitePermission;

    private OffsetDateTime lastReadAt;
}
