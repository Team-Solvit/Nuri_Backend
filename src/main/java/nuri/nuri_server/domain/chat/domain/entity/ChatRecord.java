package nuri.nuri_server.domain.chat.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "chat-record")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRecord {
    @Id
    private String id;

    private String roomId;
    private Sender sender;

    @CreatedDate
    private OffsetDateTime createdAt;

    private String contents;
    private ReplyChat replyChat;

    public static String getCollectionName() {
        return "chat-record";
    }
}
