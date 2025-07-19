package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRecordRepository extends MongoRepository<ChatRecord, String> {
    List<ChatRecord> findAllByRoomId(String roomId);
}
