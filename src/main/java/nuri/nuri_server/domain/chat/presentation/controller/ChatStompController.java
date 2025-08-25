package nuri.nuri_server.domain.chat.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.application.service.ChatStompService;
import nuri.nuri_server.domain.chat.presentation.dto.req.ChatRecordRequestDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatStompController {
    private final ChatStompService chatStompService;

    @MessageMapping("/{userId}")
    public void sendDirectMessage(@DestinationVariable String userId, Principal principal, @Valid ChatRecordRequestDto chatRecordRequestDto) {
        log.info("sendDirectMessage 진입");
        NuriUserDetails nuriUserDetails = (NuriUserDetails) principal;
        log.info(nuriUserDetails.getUsername());
        chatStompService.sendDirectMessage(userId, nuriUserDetails, chatRecordRequestDto);
    }

    @MessageMapping("/group")
    public void sendGroupMessage(Principal principal, @Valid ChatRecordRequestDto chatRecordRequestDto) {
        NuriUserDetails nuriUserDetails = (NuriUserDetails) principal;
        chatStompService.sendGroupMessage(nuriUserDetails,  chatRecordRequestDto);
    }
}
