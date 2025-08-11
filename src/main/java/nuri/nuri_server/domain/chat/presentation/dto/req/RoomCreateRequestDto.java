package nuri.nuri_server.domain.chat.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import nuri.nuri_server.domain.chat.presentation.dto.common.RoomDto;

import java.util.List;

@Builder
public record RoomCreateRequestDto (

        RoomDto roomDto,

        @NotNull
        List<String> users
) {}