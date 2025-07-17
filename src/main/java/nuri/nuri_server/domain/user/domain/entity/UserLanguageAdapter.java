package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Getter
@Table(
        name = "tbl_user_language_adapter",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "language_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLanguageAdapter extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Builder
    public UserLanguageAdapter(Language language, UserEntity user) {
        this.language = language;
        this.user = user;
    }

}
