package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRecordRepository extends MongoRepository<ChatRecord, String>, ChatRecordExtraRepository {
    List<ChatRecord> findAllByRoomId(String roomId);
}
