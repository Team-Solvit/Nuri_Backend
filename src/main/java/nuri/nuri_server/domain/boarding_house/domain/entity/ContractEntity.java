package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.contract_status.ContractStatus;
import nuri.nuri_server.domain.user.domain.entity.BoarderEntity;
import nuri.nuri_server.global.entity.BaseEntity;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tbl_contract")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarder_id", nullable = false)
    private BoarderEntity boarder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarder_room_id", nullable = false)
    private BoardingRoomEntity boarderRoom;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Builder
    public ContractEntity(BoarderEntity boarder, BoardingRoomEntity boarderRoom, LocalDate expiryDate, ContractStatus status) {
        this.boarder = boarder;
        this.boarderRoom = boarderRoom;
        this.expiryDate = expiryDate;
        this.status = Objects.requireNonNullElse(status, ContractStatus.ACTIVE);
    }

    public Boolean isActive() {
        return status == ContractStatus.ACTIVE;
    }
}
