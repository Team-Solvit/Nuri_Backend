package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_contract_period")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractPeriodEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_room_id", nullable = false)
    private BoardingRoomEntity boardingRoom;

    @Column(nullable = false, name = "contract_period")
    private Integer contractPeriod;

    @Builder
    public ContractPeriodEntity(BoardingRoomEntity boardingRoom, Integer contractPeriod) {
        this.boardingRoom = boardingRoom;
        this.contractPeriod = contractPeriod;
    }
}
