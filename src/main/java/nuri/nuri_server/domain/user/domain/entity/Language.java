package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_language")
@Getter
public class Language extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
