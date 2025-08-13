package nuri.nuri_server.domain.chat.presentation.dto.res;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record NotificationResponseDto (
        @NotNull
        String name,
        @NotNull
        String picture,
        @NotNull
        String roomId,
        @NotNull
        String contents,
        @NotNull
        OffsetDateTime sendAt
) {}
