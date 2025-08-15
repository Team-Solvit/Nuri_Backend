package nuri.nuri_server.domain.boarding_manage.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.user.domain.entity.BoarderEntity;
import nuri.nuri_server.domain.user.domain.entity.ThirdPartyEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_boarding_relationship")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRelationshipEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_party_id", nullable = false)
    private ThirdPartyEntity thirdParty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarder_id", nullable = false)
    private BoarderEntity boarder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_house_id", nullable = false)
    private BoardingHouseEntity boarderHouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_room_id", nullable = false)
    private BoardingRoomEntity boardingRoom;
}
