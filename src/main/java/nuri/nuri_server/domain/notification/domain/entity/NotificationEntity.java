package nuri.nuri_server.domain.notification.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.notification.domain.exception.NotificationUserMismatchException;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean checked;

    @Column(nullable = false)
    private String link;

    @Builder
    public NotificationEntity(UserEntity user, String title, String content, Boolean checked, String link) {
        this.user = user;
        this.content = content;
        this.checked = checked;
        this.link = link;
    }

    public void validateUser(UserEntity user) {
        if(!this.user.getId().equals(user.getId()))
            throw new NotificationUserMismatchException();
    }

    public void check() {
        this.checked = true;
    }
}