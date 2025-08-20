package nuri.nuri_server.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.follow.domain.repository.FollowRepository;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.SameProfileImageException;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.presentation.dto.res.UserProfileResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public UserProfileResponseDto getProfile(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        long followingCount = followRepository.countByFollower(userEntity);
        long followerCount = followRepository.countByFollowing(userEntity);
        long postCount = postRepository.countByUser(userEntity);

        return UserProfileResponseDto.builder()
                .followingCount(followingCount)
                .followerCount(followerCount)
                .postCount(postCount)
                .userId(userEntity.getUserId())
                .profile(userEntity.getProfile())
                .build();
    }

    public void changeProfileImage(NuriUserDetails nuriUserDetails, String profile) {
        if(nuriUserDetails.getProfile().equals(profile)) {
            throw new SameProfileImageException();
        }
        userRepository.updateProfileById(profile, nuriUserDetails.getId());
    }
}
