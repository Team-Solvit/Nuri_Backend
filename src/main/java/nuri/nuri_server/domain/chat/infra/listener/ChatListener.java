package nuri.nuri_server.domain.chat.infra.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.application.service.ChatStompService;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatListener {
    private final ChatStompService chatStompService;

    @KafkaListener(topics = "chat", concurrency = "10", groupId = "chat-group")
    public void listen(ChatRecordResponseDto chatRecordResponseDto) {
        log.info(chatRecordResponseDto.contents());
        chatStompService.listenGroupMessage(chatRecordResponseDto);
    }
}
