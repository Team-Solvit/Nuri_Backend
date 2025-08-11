package nuri.nuri_server.domain.chat.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.entity.RoomEntity;
import nuri.nuri_server.domain.chat.domain.entity.UserRoomAdapterEntity;
import nuri.nuri_server.domain.chat.domain.exception.RoomNotFoundException;
import nuri.nuri_server.domain.chat.domain.repository.ChatRecordRepository;
import nuri.nuri_server.domain.chat.domain.repository.RoomRepository;
import nuri.nuri_server.domain.chat.domain.repository.UserRoomAdapterEntityRepository;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomCreateRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomCreateResponseDto;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRecordRepository chatRecordRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomAdapterEntityRepository userRoomAdapterEntityRepository;

    @Transactional(readOnly = true)
    public List<ChatRecordResponseDto> readMessages(String roomId) {

        List<ChatRecord> chatRecords = chatRecordRepository.findAllByRoomId(roomId);
        if (chatRecords.isEmpty() && !roomId.contains(" ") && !roomRepository.existsById(UUID.fromString(roomId))) {
            throw new RoomNotFoundException(roomId);
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

    @Transactional
    public RoomCreateResponseDto createRoom(RoomCreateRequestDto input) {
        RoomEntity room = RoomEntity.builder()
                .name(input.roomDto().name())
                .profile(input.roomDto().profile())
                .build();
        roomRepository.save(room);

        for(String userId : input.users()) {
            UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
            userRoomAdapterEntityRepository.save(UserRoomAdapterEntity.builder()
                    .room(room)
                    .user(user)
                    .build()
            );
        }

        return RoomCreateResponseDto.builder()
                .id(room.getId())
                .users(input.users())
                .room(input.roomDto())
                .build();
    }
}
