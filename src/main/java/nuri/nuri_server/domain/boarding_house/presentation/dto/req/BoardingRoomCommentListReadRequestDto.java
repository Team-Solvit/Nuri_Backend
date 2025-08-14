package nuri.nuri_server.domain.boarding_house.presentation.dto.req;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BoardingRoomCommentListReadRequestDto(
        @NotNull(message = "댓글 조회시 시작위치(start)는 필수 항목입니다.")
        Integer start,

        @NotNull(message = "댓글 조회시 하숙방 아이디(roomId)는 필수 항목입니다.")
        UUID roomId
) {}