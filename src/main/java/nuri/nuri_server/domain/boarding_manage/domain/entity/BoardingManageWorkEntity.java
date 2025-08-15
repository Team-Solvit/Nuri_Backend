package nuri.nuri_server.domain.boarding_manage.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.boarding_manage.domain.manage_type.BoardingManageType;
import nuri.nuri_server.global.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_boarding_manage_work")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingManageWorkEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relationship_id", nullable = false)
    private BoardingRelationshipEntity boardingRelationship;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    private String file;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardingManageType type;

    @Column(nullable = false)
    private Boolean status;

    public void complete() {
        status = true;
    }

    public void incomplete() {
        status = false;
    }
}
