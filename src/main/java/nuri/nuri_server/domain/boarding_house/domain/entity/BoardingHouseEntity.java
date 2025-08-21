package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.gender.Gender;
import nuri.nuri_server.domain.user.domain.entity.HostEntity;
import nuri.nuri_server.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_boarding_house")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingHouseEntity extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private HostEntity host;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "house_call_number")
    private String houseCallNumber;

    @Column
    private String description;

    @Column(name = "nearest_station")
    private String nearestStation;

    @Column(name = "nearest_school")
    private String nearestSchool;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, name = "is_meal_provided")
    private Boolean isMealProvided;

    @OneToMany(mappedBy = "boardingHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardingRoomEntity> boardingRooms = new ArrayList<>();
}
