package nuri.nuri_server.domain.chat.presentation.controller;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.application.service.ChatService;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomCreateRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomCreateResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomReadResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @QueryMapping
    public List<ChatRecordResponseDto> readMessages(@Argument("room_id") String roomId) {
        return chatService.readMessages(roomId);
    }

    @MutationMapping
    public RoomCreateResponseDto createRoom(@Argument("input") RoomCreateRequestDto input) {
        return chatService.createRoom(input);
    }

    @QueryMapping
    public List<RoomReadResponseDto> getRooms(@AuthenticationPrincipal NuriUserDetails nuriUserDetails) {
        return chatService.readRooms(nuriUserDetails);
    }
}
