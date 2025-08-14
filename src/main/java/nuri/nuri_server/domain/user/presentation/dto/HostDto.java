package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.HostEntity;

@Builder
public record HostDto(
        String callNumber,
        UserDto user
) {
    public static HostDto from(HostEntity hostEntity) {
        UserDto user = UserDto.from(hostEntity.getUser());
        return HostDto.builder()
                .callNumber(hostEntity.getCallNumber())
                .user(user)
                .build();
    }
}
