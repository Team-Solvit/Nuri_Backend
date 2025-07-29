package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_language")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LanguageEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;
}
