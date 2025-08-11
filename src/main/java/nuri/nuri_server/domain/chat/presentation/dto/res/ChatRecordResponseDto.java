package nuri.nuri_server.domain.chat.presentation.dto.res;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import nuri.nuri_server.domain.chat.domain.entity.ReplyChat;
import nuri.nuri_server.domain.chat.domain.entity.Sender;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ChatRecordResponseDto (
    @NotNull
    UUID id,

    String roomId,

    @NotNull
    Sender sender,

    @NotNull
    OffsetDateTime createdAt,

    @NotNull
    String contents,

    ReplyChat replyChat
) {}