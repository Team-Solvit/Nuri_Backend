package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @JoinColumn(nullable = false, name = "language_id")
    private LanguageEntity language;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    private String password;

    @Column(nullable = false)
    private String email;

    private String introduce;

    private String profile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // OAuth 전용
    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_id")
    private String oauthId;

    @Builder(builderMethodName = "signupBuilder")
    public UserEntity(String userId, CountryEntity country, LanguageEntity language, String name, String password, String email, String introduce, String profile, Role role, String oauthProvider, String oauthId) {
        this.userId = userId;
        this.country = country;
        this.language = language;
        this.name = name;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profile = profile;
        this.role = role;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
    }
}
