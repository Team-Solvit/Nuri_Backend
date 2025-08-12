package nuri.nuri_server.domain.boarding_house.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomCommentEntity;
import nuri.nuri_server.domain.user.presentation.dto.UserInfo;

import java.util.UUID;

@Builder
public record BoardingRoomCommentInfo(
        UUID commentId,
        UUID roomId,
        UserInfo commenter,
        String content
) {
    public static BoardingRoomCommentInfo from(BoardingRoomCommentEntity comment) {
        UserInfo commenter = UserInfo.from(comment.getUser());
        return BoardingRoomCommentInfo.builder()
                .commentId(comment.getId())
                .roomId(comment.getBoardingRoom().getId())
                .commenter(commenter)
                .content(comment.getContents())
                .build();
    }
}
