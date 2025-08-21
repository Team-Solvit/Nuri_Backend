package nuri.nuri_server.domain.post.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;

@Builder
public record BoardingPost(
        BoardingRoomDto room,
        Long likeCount,
        Long commentCount
) implements PostInfo {}
