package nuri.nuri_server.domain.chat.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import nuri.nuri_server.domain.chat.domain.entity.ReplyChat;

@Builder
public record ChatRecordRequestDto (
        @NotBlank(message = "방 아이디가 존재해야 합니다.")
        String roomId,
        @NotBlank(message = "내용이 존재해야 합니다.")
        String contents,
        ReplyChat replyChat
) {}