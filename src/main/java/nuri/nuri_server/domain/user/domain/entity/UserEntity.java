package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Language language;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String introduce;

    private String profile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(builderMethodName = "signupBuilder")
    public UserEntity(String userId, CountryEntity country, Language language, String name, String password, String email, String introduce, String profile, Role role) {
        this.userId = userId;
        this.country = country;
        this.language = language;
        this.name = name;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profile = profile;
        this.role = role;
    }
}
