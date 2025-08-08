package nuri.nuri_server.domain.post.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.post.domain.exception.PostAuthorMismatchException;
import nuri.nuri_server.domain.post.domain.share_range.ShareRange;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostFileEntity> postFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTagEntity> postHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> postComments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLikeEntity> postLikeEntities = new ArrayList<>();


    @Builder
    public PostEntity(UserEntity user, String title, String contents, ShareRange shareRange, Boolean isGroup) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.shareRange = shareRange;
        this.isGroup = isGroup;
    }

    public void updatePost(String title, String contents, ShareRange shareRange, Boolean isGroup) {
        this.title = title;
        this.contents = contents;
        this.shareRange = shareRange;
        this.isGroup = isGroup;
    }

    public void validateAuthor(UserEntity requestUser) {
        if(!this.user.getId().equals(requestUser.getId()))
            throw new PostAuthorMismatchException();
    }
}
