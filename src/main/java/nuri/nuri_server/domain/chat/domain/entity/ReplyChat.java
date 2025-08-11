package nuri.nuri_server.domain.chat.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyChat {
    private String chatId;
    private String contents;
    private String name;
}
