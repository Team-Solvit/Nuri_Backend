package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_boarding_room_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRoomLikeEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "boarding_room_id")
    private BoardingRoomEntity boardingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Builder
    public BoardingRoomLikeEntity(BoardingRoomEntity boardingRoom, UserEntity user) {
        this.boardingRoom = boardingRoom;
        this.user = user;
    }
}
