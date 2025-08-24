package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.BoarderEntity;
import nuri.nuri_server.domain.user.domain.gender.Gender;

@Builder
public record BoarderDto(
        String callNumber,
        Gender gender,
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