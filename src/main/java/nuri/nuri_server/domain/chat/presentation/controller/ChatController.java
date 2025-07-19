package nuri.nuri_server.domain.chat.presentation.controller;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.application.service.ChatService;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @QueryMapping
    public List<ChatRecord> getMessages(@Argument("room_id") String roomId) {
        return chatService.getMessages(roomId);
    }
}
