package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomInfo;

@Builder
public record BoardingPostInfo(
        BoardingRoomInfo room,
        Long likeCount,
        Long commentCount
) implements PostListInfo {}
