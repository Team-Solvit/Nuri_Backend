package nuri.nuri_server.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.presentation.dto.res.UserProfileResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserProfileResponseDto> queryUsers(String userId) {
        List<UserEntity> userEntities = userRepository.findAllByUserIdLike(userId);
        return userEntities.stream().map(userEntity ->
                UserProfileResponseDto.builder()
                        .name(userEntity.getName())
                        .profile(userEntity.getProfile())
                        .userId(userEntity.getUserId())
                        .build()
        ).toList();
    }
}
