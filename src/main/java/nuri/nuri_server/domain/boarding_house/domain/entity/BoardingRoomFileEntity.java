package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_boarding_room_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRoomFileEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "boarding_room_id")
    private BoardingRoomEntity boardingRoom;

    @Column(nullable = false, name = "media_url")
    private String mediaUrl;

    @Builder
    public BoardingRoomFileEntity(BoardingRoomEntity boardingRoom, String mediaUrl) {
        this.boardingRoom = boardingRoom;
        this.mediaUrl = mediaUrl;
    }
}
