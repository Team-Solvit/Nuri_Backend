package nuri.nuri_server.domain.user.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.user.domain.entity.BoarderEntity;

@Builder
public record BoarderInfo(
        String callNumber,
        String gender,
        UserInfo userInfo
) {
    public static BoarderInfo from(BoarderEntity boarder) {
        UserInfo user = UserInfo.from(boarder.getUser());
        return BoarderInfo.builder()
                .callNumber(boarder.getCallNumber())
                .gender(boarder.getGender())
                .userInfo(user)
                .build();
    }
}