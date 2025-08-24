package nuri.nuri_server.domain.user.presentation.dto.res;

import lombok.Builder;

@Builder
public record UserProfileResponseDto (
        String profile,
        String name,
        String userId
) {}
