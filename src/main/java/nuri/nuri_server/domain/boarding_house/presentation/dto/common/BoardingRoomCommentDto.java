package nuri.nuri_server.domain.boarding_house.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomCommentEntity;
import nuri.nuri_server.domain.user.presentation.dto.UserDto;

import java.util.UUID;

@Builder
public record BoardingRoomCommentDto(
        UUID commentId,
        UUID roomId,
        UserDto commenter,
        String content
) {
    public static BoardingRoomCommentDto from(BoardingRoomCommentEntity comment) {
        UserDto commenter = UserDto.from(comment.getUser());
        return BoardingRoomCommentDto.builder()
                .commentId(comment.getId())
                .roomId(comment.getBoardingRoom().getId())
                .commenter(commenter)
                .content(comment.getContents())
                .build();
    }
}
