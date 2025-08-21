package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.BoarderEntity;

@Builder
public record BoarderDto(
        String callNumber,
        String gender,
        UserDto user
) {
    public static BoarderDto from(BoarderEntity boarder) {
        UserDto user = UserDto.from(boarder.getUser());
        return BoarderDto.builder()
                .callNumber(boarder.getCallNumber())
                .gender(boarder.getGender())
                .user(user)
                .build();
    }
}