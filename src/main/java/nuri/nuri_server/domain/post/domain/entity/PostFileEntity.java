package nuri.nuri_server.domain.post.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_post_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFileEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "post_id")
    private PostEntity post;

    @Column(nullable = false, name = "media_url")
    private String mediaUrl;

    @Builder
    public PostFileEntity(PostEntity post, String mediaUrl) {
        this.post = post;
        this.mediaUrl = mediaUrl;
    }
}
