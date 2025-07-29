package nuri.nuri_server.domain.post.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_hash_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTagEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "post_id")
    private PostEntity post;

    @Column(nullable = false)
    private String name;

    @Builder
    public HashTagEntity(PostEntity post, String name) {
        this.post = post;
        this.name = name;
    }
}
