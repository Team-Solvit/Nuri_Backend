package nuri.nuri_server.domain.chat.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.entity.Sender;
import nuri.nuri_server.domain.chat.domain.repository.ChatRecordRepository;
import nuri.nuri_server.domain.chat.domain.repository.RoomRepository;
import nuri.nuri_server.domain.chat.domain.repository.UserRoomAdapterEntityRepository;
import nuri.nuri_server.domain.chat.presentation.dto.req.ChatRecordRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.NotificationResponseDto;
import nuri.nuri_server.global.properties.ChatProperties;
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
    private final KafkaTemplate<String, NotificationResponseDto> kafkaTemplate;
    private final RoomRepository roomRepository;
    private final ChatProperties chatProperties;

    @Transactional
    public void sendDirectMessage(String userId, NuriUserDetails principal, ChatRecordRequestDto chatRecordRequestDto) {
        NotificationResponseDto notificationResponseDto = saveMessage(principal, chatRecordRequestDto);
        simpMessagingTemplate.convertAndSendToUser(userId, "/messages", notificationResponseDto);
    }

    @Transactional
    public void sendGroupMessage(NuriUserDetails principal, ChatRecordRequestDto chatRecordRequestDto) {
        NotificationResponseDto notificationResponseDto = saveMessage(principal, chatRecordRequestDto);
        kafkaTemplate.send("chat", notificationResponseDto);
    }

    private NotificationResponseDto saveMessage(NuriUserDetails principal, ChatRecordRequestDto chatRecordRequestDto) {
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

        String picture = sender.getProfile();

        if(!chatRecordRequestDto.roomId().contains(":")) {
            picture = roomRepository.findPictureById(UUID.fromString(chatRecordRequestDto.roomId()));
        }

        return NotificationResponseDto.builder()
                .sendAt(OffsetDateTime.now())
                .contents(chatRecordRequestDto.contents())
                .roomId(chatRecordRequestDto.roomId())
                .name(principal.getNickname())
                .picture(picture)
                .build();
    }

    public void listenGroupMessage(NotificationResponseDto notificationResponseDto) {
        UUID roomId = UUID.fromString(notificationResponseDto.roomId());

        List<String> users = userRoomAdapterEntityRepository.findUsersByRoomId(roomId);

        if((long) users.size() > chatProperties.getBroadcastThreshold()) {
            simpMessagingTemplate.convertAndSend("/messages/" + notificationResponseDto.roomId(), notificationResponseDto);
        }
        else {
            users.forEach(userId ->
                    simpMessagingTemplate.convertAndSendToUser(userId, "/messages", notificationResponseDto)
            );
        }
    }
}
