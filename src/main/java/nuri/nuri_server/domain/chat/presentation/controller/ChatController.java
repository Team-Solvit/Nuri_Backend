package nuri.nuri_server.domain.chat.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.application.service.ChatService;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomCreateRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomInviteRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomCreateResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomReadResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class   ChatController {
    private final ChatService chatService;

    @QueryMapping
    public List<ChatRecordResponseDto> readMessages(@Argument @NotNull(message = "방 아이디 값은 존재해야 합니다.") String roomId) {
        log.info("readMessages 인자값 : {}", roomId);
        return chatService.readMessages(roomId);
    }

    @MutationMapping
    public RoomCreateResponseDto createRoom(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument RoomCreateRequestDto roomCreateRequestDto) {
        log.info("createRoom 인자값 : {}, {}", nuriUserDetails.getUsername(), roomCreateRequestDto.toString());
        return chatService.createRoom(nuriUserDetails, roomCreateRequestDto);
    }

    @QueryMapping
    public List<RoomReadResponseDto> getRooms(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "페이지 값은 존재해야 합니다.") Integer page, @Argument @NotNull(message = "사이즈 값은 존재해야 합니다.") Integer size) {
        log.info("getRooms 인자값 : {}, {}, {}", nuriUserDetails.getUsername(), page, size);
        return chatService.getRooms(nuriUserDetails, PageRequest.of(page, size));
    }

    @QueryMapping
    public List<String> getRoomsGroupChat(@AuthenticationPrincipal NuriUserDetails nuriUserDetails) {
        log.info("getRoomsGroupChat 인자값 : {}", nuriUserDetails.getUsername());
        return chatService.getRoomsGroupChat(nuriUserDetails);
    }

    @MutationMapping
    public Boolean invite(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument RoomInviteRequestDto roomInviteRequestDto) {
        log.info("invite 인자값 : {}, {}", nuriUserDetails.getUsername(), roomInviteRequestDto.toString());
        chatService.invite(nuriUserDetails, roomInviteRequestDto);
        return true;
    }

    @MutationMapping
    public Boolean exit(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "방 아이디는 존재해야 합니다.") String roomId) {
        log.info("exit 인자값 : {}, {}", nuriUserDetails.getUsername(), roomId);
        chatService.exit(nuriUserDetails, roomId);
        return true;
    }

    @MutationMapping
    public Boolean kick(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "방 아이디는 존재해야 합니다.") String roomId, @Argument @NotNull(message = "추방시킬 유저가 존재해야 합니다.") String userId) {
        log.info("kick 인자값 : {}, {}, {}", nuriUserDetails.getUsername(), roomId, userId);
        chatService.kick(nuriUserDetails, roomId, userId);
        return true;
    }

    @MutationMapping
    public Boolean exitRoom(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message = "방 아이디는 존재해야 합니다.") String roomId) {
        log.info("exitRoom 인자값 : {}, {}", nuriUserDetails.getUsername(), roomId);
        chatService.exitRoom(nuriUserDetails, roomId);
        return true;
    }
}
