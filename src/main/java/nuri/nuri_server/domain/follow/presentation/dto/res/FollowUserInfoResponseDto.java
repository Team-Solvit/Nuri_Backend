package nuri.nuri_server.domain.follow.presentation.dto.res;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FollowUserInfoResponseDto(
        UUID id,
        String userId,
        String profile
) {}
