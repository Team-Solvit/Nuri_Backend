package nuri.nuri_server.domain.boarding_house.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BoardingRoomCommentCreateRequestDto(
        @NotNull(message = "댓글 달기 시 하숙방 아이디(roomId)는 필수 항목입니다.")
        UUID roomId,

        @NotBlank(message = "댓글 달기 시 내용(contents)은 필수 항목입니다.")
        String contents
) {}
