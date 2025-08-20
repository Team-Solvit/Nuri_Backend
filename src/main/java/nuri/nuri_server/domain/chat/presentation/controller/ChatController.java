package nuri.nuri_server.domain.chat.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.application.service.ChatService;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomCreateRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomInviteRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomCreateResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomReadResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<ChatRecordResponseDto> readMessages(@Argument @NotNull(message = "방 아이디 값은 존재해야 합니다.") String roomId) {
        return chatService.readMessages(roomId);
    }

    @MutationMapping
    public RoomCreateResponseDto createRoom(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument RoomCreateRequestDto roomCreateRequestDto) {
        return chatService.createRoom(nuriUserDetails, roomCreateRequestDto);
    }

    @QueryMapping
    public Page<RoomReadResponseDto> getRooms(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "페이지 값은 존재해야 합니다.") Integer page, @Argument @NotNull(message = "사이즈 값은 존재해야 합니다.") int size) {
        return chatService.getRooms(nuriUserDetails, PageRequest.of(page, size));
    }

    @QueryMapping
    public List<String> getRoomsGroupChat(@AuthenticationPrincipal NuriUserDetails nuriUserDetails) {
        return chatService.getRoomsGroupChat(nuriUserDetails);
    }

    @MutationMapping
    public Boolean invite(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument RoomInviteRequestDto roomInviteRequestDto) {
        chatService.invite(nuriUserDetails, roomInviteRequestDto);
        return true;
    }

    @MutationMapping
    public Boolean exit(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "방 아이디는 존재해야 합니다.") String roomId) {
        chatService.exit(nuriUserDetails, roomId);
        return true;
    }

    @MutationMapping
    public Boolean kick(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "방 아이디는 존재해야 합니다.") String roomId, @Argument @NotNull(message = "추방시킬 유저가 존재해야 합니다.") String userId) {
        chatService.kick(nuriUserDetails, roomId, userId);
        return true;
    }

    @MutationMapping
    public Boolean exitRoom(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "방 아이디는 존재해야 합니다.") String roomId) {
        chatService.exitRoom(nuriUserDetails, roomId);
        return true;
    }
}
