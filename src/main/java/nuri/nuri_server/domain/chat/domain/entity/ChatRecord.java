package nuri.nuri_server.domain.chat.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat-record")
public class ChatRecord {
    @Id
    private String Id;

    private Long roomId;
    private Sender sender;

    @CreatedDate
    private LocalDateTime createdAt;

    private String contents;
    private ReplyChat replyChat;
}
