package nuri.nuri_server.domain.post.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.post.domain.share_range.ShareRange;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    private String title;

    private String contents;

    @Column(nullable = false, name = "share_range")
    @Enumerated(EnumType.STRING)
    private ShareRange shareRange;

    @Column(nullable = false, name = "is_group")
    private Boolean isGroup;

    @Builder
    public PostEntity(UserEntity user, String title, String contents, ShareRange shareRange, Boolean isGroup) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.shareRange = shareRange;
        this.isGroup = isGroup;
    }
}
