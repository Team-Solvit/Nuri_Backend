package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRecordRepository extends MongoRepository<ChatRecord, String> {
}
