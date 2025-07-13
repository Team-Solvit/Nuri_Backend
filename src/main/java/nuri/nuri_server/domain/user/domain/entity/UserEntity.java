package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.domain.role.Role;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CountryEntity country;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String introduce;

    private String profile;

    @Column(nullable = false)
    private Role role;

    @Builder(builderMethodName = "signupBuilder")
    public UserEntity(String id, CountryEntity country, String name, String password, String email, String introduce, String profile, Role role) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profile = profile;
        this.role = role;
    }
}
