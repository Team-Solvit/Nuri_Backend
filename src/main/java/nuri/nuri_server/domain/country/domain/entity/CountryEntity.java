package nuri.nuri_server.domain.country.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_country")
@Getter
public class CountryEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
