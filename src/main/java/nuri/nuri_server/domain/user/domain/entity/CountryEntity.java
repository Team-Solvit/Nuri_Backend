package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_country")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountryEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
