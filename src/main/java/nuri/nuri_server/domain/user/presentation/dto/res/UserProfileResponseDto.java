package nuri.nuri_server.domain.user.presentation.dto.res;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserProfileResponseDto(
    @NotNull
    Long postCount,
    @NotNull
    Long followerCount,
    @NotNull
    Long followingCount,
    @NotNull
    String profile,
    @NotNull
    String userId
) {}
