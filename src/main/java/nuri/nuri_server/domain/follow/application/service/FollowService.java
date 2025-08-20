package nuri.nuri_server.domain.follow.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.follow.domain.entity.FollowEntity;
import nuri.nuri_server.domain.follow.domain.exception.DuplicateFollowException;
import nuri.nuri_server.domain.follow.domain.exception.FollowNotFoundException;
import nuri.nuri_server.domain.follow.domain.repository.FollowRepository;
import nuri.nuri_server.domain.follow.presentation.dto.res.FollowUserInfoResponseDto;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(NuriUserDetails nuriUserDetails, String targetId) {
        UserEntity userEntity = nuriUserDetails.getUser();
        UserEntity target = userRepository.findByUserId(targetId).orElseThrow(() -> new UserNotFoundException(targetId));

        if(followRepository.existsByFollowerAndFollowing(userEntity, target)) {
            throw new DuplicateFollowException();
        }

        FollowEntity followEntity = FollowEntity.builder()
                .follower(userEntity)
                .following(target)
                .build();

        followRepository.save(followEntity);
    }

    @Transactional
    public void unfollow(NuriUserDetails nuriUserDetails, String targetId) {
        UserEntity userEntity = nuriUserDetails.getUser();
        UserEntity target = userRepository.findByUserId(targetId).orElseThrow(() -> new UserNotFoundException(targetId));

        if(!followRepository.existsByFollowerAndFollowing(userEntity, target)) {
            throw new FollowNotFoundException();
        }

        followRepository.deleteByFollowerAndFollowing(userEntity, target);
    }

    @Transactional(readOnly = true)
    public List<FollowUserInfoResponseDto> getFollowerInfo(String targetId) {
        UserEntity target = userRepository.findByUserId(targetId).orElseThrow(() -> new UserNotFoundException(targetId));

        List<FollowEntity> followers = followRepository.findAllByFollowing(target);


    }

    @Transactional(readOnly = true)
    public List<FollowUserInfoResponseDto> getFollowingInfo(String targetId) {

    }
}
