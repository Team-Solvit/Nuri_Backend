package nuri.nuri_server.domain.follow.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FollowEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "follower_id")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(nullable = false, name = "following_id")
    private UserEntity following;

    @Builder
    public FollowEntity(UserEntity follower, UserEntity following) {
        this.follower = follower;
        this.following = following;
    }
}
