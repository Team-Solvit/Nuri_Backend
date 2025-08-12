package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_boarding_room_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRoomCommentEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "boarding_room_id")
    private BoardingRoomEntity boardingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String contents;

    @Builder
    public BoardingRoomCommentEntity(BoardingRoomEntity boardingRoom, UserEntity user, String contents) {
        this.boardingRoom = boardingRoom;
        this.user = user;
        this.contents = contents;
    }
}
