package nuri.nuri_server.domain.chat.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.repository.ChatRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRecordRepository chatRecordRepository;

    public List<ChatRecord> getMessages(String roomId) {

        return chatRecordRepository.findAllByRoomId(roomId);
    }
}
