package nuri.nuri_server.domain.chat.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.entity.Sender;
import nuri.nuri_server.domain.chat.domain.repository.ChatRecordRepository;
import nuri.nuri_server.domain.chat.domain.repository.UserRoomAdapterEntityRepository;
import nuri.nuri_server.domain.chat.presentation.dto.req.ChatRecordRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatStompService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRecordRepository chatRecordRepository;
    private final UserRoomAdapterEntityRepository userRoomAdapterEntityRepository;
    private final KafkaTemplate<String, ChatRecordResponseDto> kafkaTemplate;

    @Transactional
    public void sendDirectMessage(String userId, NuriUserDetails principal, ChatRecordRequestDto chatRecordRequestDto) {
        ChatRecordResponseDto chatRecordResponseDto = saveMessage(principal, chatRecordRequestDto);
        simpMessagingTemplate.convertAndSendToUser(userId, "/messages", chatRecordResponseDto);
    }

    @Transactional
    public void sendGroupMessage(NuriUserDetails principal, ChatRecordRequestDto chatRecordRequestDto) {
        ChatRecordResponseDto chatRecordResponseDto = saveMessage(principal, chatRecordRequestDto);
        kafkaTemplate.send("chat", chatRecordResponseDto);
    }

    private ChatRecordResponseDto saveMessage(NuriUserDetails principal, ChatRecordRequestDto chatRecordRequestDto) {
        UUID uuid = UUID.randomUUID();

        Sender sender = Sender.builder()
                .name(principal.getName())
                .profile(principal.getProfile())
                .build();

        ChatRecord chatRecord = ChatRecord.builder()
                .id(uuid.toString())
                .roomId(chatRecordRequestDto.roomId())
                .contents(chatRecordRequestDto.contents())
                .replyChat(chatRecordRequestDto.replyChat())
                .sender(sender)
                .createdAt(OffsetDateTime.now())
                .build();

        chatRecordRepository.save(chatRecord);

        return ChatRecordResponseDto.builder()
                .id(uuid)
                .replyChat(chatRecordRequestDto.replyChat())
                .sender(sender)
                .roomId(chatRecordRequestDto.roomId())
                .contents(chatRecordRequestDto.contents())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public void listenGroupMessage(ChatRecordResponseDto chatRecordResponseDto) {
        List<UserEntity> users = userRoomAdapterEntityRepository.findUsersByRoomId(UUID.fromString(chatRecordResponseDto.roomId()));
    }
}
