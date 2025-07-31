package nuri.nuri_server.domain.post.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.post.domain.exception.CommenterMismatchException;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String contents;

    @Builder
    public CommentEntity(PostEntity post, UserEntity user, String contents) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void validateCommenter(UserEntity requestUser) {
        if(!this.user.getId().equals(requestUser.getId()))
            throw new CommenterMismatchException();
    }

    public void edit(String content) {
        this.contents = content;
    }
}
