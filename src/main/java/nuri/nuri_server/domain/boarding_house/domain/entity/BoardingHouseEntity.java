package nuri.nuri_server.domain.boarding_house.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.HostEntity;
import nuri.nuri_server.global.entity.BaseEntity;

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

    @Column
    private String houseCallNumber;
}
