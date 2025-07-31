package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_boarding_room_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRoomOptionEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_room_id", nullable = false)
    private BoardingRoomEntity boardingRoom;

    @Column(nullable = false, name = "option_name")
    private String optionName;

    @Builder
    public BoardingRoomOptionEntity(BoardingRoomEntity boardingRoom, String optionName) {
        this.boardingRoom = boardingRoom;
        this.optionName = optionName;
    }
}
