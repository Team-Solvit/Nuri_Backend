package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.HostEntity;

@Builder
public record HostInfo(
        String callNumber,
        UserInfo userInfo
) {
    public static HostInfo from(HostEntity hostEntity) {
        UserInfo user = UserInfo.from(hostEntity.getUser());
        return HostInfo.builder()
                .callNumber(hostEntity.getCallNumber())
                .userInfo(user)
                .build();
    }
}
