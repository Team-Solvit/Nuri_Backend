package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.role.Role;

import java.util.UUID;

@Builder
public record UserInfo(
        UUID id,
        String userId,
        UUID country,
        UUID language,
        String name,
        String email,
        String introduce,
        String profile,
        Role role
) {
    public static UserInfo from(UserEntity user) {
        return UserInfo.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .country(user.getCountry().getId())
                .language(user.getLanguage().getId())
                .name(user.getName())
                .email(user.getEmail())
                .introduce(user.getIntroduce())
                .profile(user.getProfile())
                .role(user.getRole())
                .build();
    }
}
