package nuri.nuri_server.domain.chat.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.exception.RoomNotFoundException;
import nuri.nuri_server.domain.chat.domain.repository.ChatRecordRepository;
import nuri.nuri_server.domain.chat.domain.repository.RoomRepository;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRecordRepository chatRecordRepository;
    private final RoomRepository roomRepository;

    public List<ChatRecordResponseDto> readMessages(UUID roomId) {

        List<ChatRecord> chatRecords = chatRecordRepository.findAllByRoomId(roomId.toString());
        if (chatRecords.isEmpty() && !roomRepository.existsById(roomId)) {
            throw new RoomNotFoundException(roomId.toString());
        }

        return chatRecords.stream().map(
                (chatRecord) -> ChatRecordResponseDto.builder()
                        .id(UUID.fromString(chatRecord.getId()))
                        .roomId(roomId)
                        .sender(chatRecord.getSender())
                        .createdAt(chatRecord.getCreatedAt())
                        .contents(chatRecord.getContents())
                        .replyChat(chatRecord.getReplyChat())
                        .build()
        ).toList();
    }


}
