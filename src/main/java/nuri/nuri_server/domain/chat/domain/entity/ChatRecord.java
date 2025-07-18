package nuri.nuri_server.domain.chat.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "chat-record")
public class ChatRecord {
    @Id
    private UUID id;

    private UUID roomId;
    private Sender sender;

    @CreatedDate
    private LocalDateTime createdAt;

    private String contents;
    private ReplyChat replyChat;
}
