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
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomReadResponseDto;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public Page<RoomReadResponseDto> readRooms(NuriUserDetails nuriUserDetails, Pageable pageable) {
        Page<RoomEntity> rooms = userRoomAdapterEntityRepository.findRoomsByUserId(nuriUserDetails.getName(), pageable);
        List<String> roomIds = rooms.stream()
                .map(room -> room.getId().toString())
                .toList();
        List<ChatRecord> latestChatRecords = chatRecordRepository.findLatestMessagesByRoomIds(roomIds);

        Map<String, ChatRecord> latestMessageMap = latestChatRecords.stream()
                .collect(Collectors.toMap(ChatRecord::getRoomId, Function.identity()));

        List<RoomReadResponseDto> roomReadResponseDtoList = rooms.stream()
                .sorted(Comparator.comparing(
                        room -> latestMessageMap.get(room.getId().toString()) == null,
                        Comparator.reverseOrder()))
                .map(room -> {
                    ChatRecord latestMessage = latestMessageMap.get(room.getId().toString());
                    return RoomReadResponseDto.from(latestMessage, room);
                })
                .toList();

        return new PageImpl<>(roomReadResponseDtoList, pageable, rooms.getTotalElements());
    }
}
