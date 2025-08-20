package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface ChatRecordExtraRepository {
    Page<ChatRecord> findLatestMessagesByRoomIds(List<String> roomIds, Pageable pageable);

    long countByRoomIdAndCreatedAtAfter(String roomId, OffsetDateTime lastReadAt);
}
