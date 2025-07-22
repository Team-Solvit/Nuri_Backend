package nuri.nuri_server.domain.chat.presentation.dto.res;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import nuri.nuri_server.domain.chat.domain.entity.ReplyChat;
import nuri.nuri_server.domain.chat.domain.entity.Sender;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class ChatRecordResponseDto {
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private Sender sender;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private String contents;

    private ReplyChat replyChat;
}
