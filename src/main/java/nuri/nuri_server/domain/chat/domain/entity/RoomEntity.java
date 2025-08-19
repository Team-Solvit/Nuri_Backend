package nuri.nuri_server.domain.chat.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Getter
@Table(name = "tbl_room")
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String profile;
    @Column(nullable = false)
    private Boolean isTeam;
}
