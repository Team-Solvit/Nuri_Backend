package nuri.nuri_server.domain.chat.domain.repository;

import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;

import java.util.List;

public interface ChatRecordExtraRepository {
    List<ChatRecord> findLatestMessagesByRoomIds(List<String> roomIds);
}
