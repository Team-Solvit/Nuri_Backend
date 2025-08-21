package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.boarding_status.BoardingStatus;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomHostMismatchException;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_boarding_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRoomEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_house_id", nullable = false)
    private BoardingHouseEntity boardingHouse;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, name = "monthly_rent")
    private Integer monthlyRent;

    @Column(nullable = false, name = "head_count")
    private Integer headCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardingStatus status;

    @OneToMany(mappedBy = "boarderRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractEntity> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "boardingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardingRoomCommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "boardingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardingRoomFileEntity> files = new ArrayList<>();

    @OneToMany(mappedBy = "boardingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardingRoomLikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "boardingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardingRoomOptionEntity> options = new ArrayList<>();

    @OneToMany(mappedBy = "boardingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractPeriodEntity> contractPeriods = new ArrayList<>();

    @Builder
    public BoardingRoomEntity(BoardingHouseEntity boardingHouse, String name, String description, Integer monthlyRent, Integer headCount, BoardingStatus status) {
        this.boardingHouse = boardingHouse;
        this.name = name;
        this.description = description;
        this.monthlyRent = monthlyRent;
        this.headCount = headCount;
        this.status = Objects.requireNonNullElse(status, BoardingStatus.EMPTY_ROOM);
    }

    public void updateBoardingRoom(String name, String description, Integer monthlyRent, Integer headCount) {
        this.name = name;
        this.description = description;
        this.monthlyRent = monthlyRent;
        this.headCount = headCount;
    }

    public void validateHost(UserEntity requestUser) {
        if(!this.boardingHouse.getHost().getId().equals(requestUser.getId()))
            throw new BoardingRoomHostMismatchException();
    }
}
