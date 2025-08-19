package nuri.nuri_server.domain.chat.infra.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.application.service.ChatStompService;
import nuri.nuri_server.domain.chat.presentation.dto.req.UserExitRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.req.UserJoinRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.NotificationResponseDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatListener {
    private final ChatStompService chatStompService;

    @KafkaListener(topics = "chat", concurrency = "10", groupId = "chat-group")
    public void listenChat(NotificationResponseDto notificationResponseDto) {
        log.info(notificationResponseDto.contents());
        chatStompService.listenGroupMessage(notificationResponseDto);
    }

    @KafkaListener(topics = "user-join", concurrency = "4", groupId = "chat-group")
    public void listenUserJoin(UserJoinRequestDto userJoinRequestDto) {
        chatStompService.joinUser(userJoinRequestDto);
    }

    @KafkaListener(topics = "user-exit", concurrency = "4", groupId = "chat-group")
    public void listenUserExit(UserExitRequestDto userExitRequestDto) {
        chatStompService.exitUser(userExitRequestDto);
    }
}
