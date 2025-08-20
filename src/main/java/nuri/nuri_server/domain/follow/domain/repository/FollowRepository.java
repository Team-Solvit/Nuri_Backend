package nuri.nuri_server.domain.follow.domain.repository;

import nuri.nuri_server.domain.follow.domain.entity.FollowEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, UUID> {
    boolean existsByFollowerAndFollowing(UserEntity follower, UserEntity following);

    void deleteByFollowerAndFollowing(UserEntity follower, UserEntity following);

    @Query("select f.follower from FollowEntity f where f.following = :following")
    List<UserEntity> findAllFollowerByFollowing(UserEntity following);

    @Query("select f.following from FollowEntity f where f.follower = :follower")
    List<UserEntity> findAllFollowingByFollower(UserEntity follower);
}
