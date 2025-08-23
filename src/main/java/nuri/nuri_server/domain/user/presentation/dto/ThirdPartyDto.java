package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.ThirdPartyEntity;

@Builder
public record ThirdPartyDto(
        UserDto user
) {
    public static ThirdPartyDto from(ThirdPartyEntity thirdParty) {
        UserDto user = UserDto.from(thirdParty.getUser());
        return ThirdPartyDto.builder()
                .user(user)
                .build();
    }
}
