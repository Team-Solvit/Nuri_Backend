package nuri.nuri_server.domain.chat.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.domain.exception.UnauthorizedInvitationException;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.entity.RoomEntity;
import nuri.nuri_server.domain.chat.domain.entity.UserRoomAdapterEntity;
import nuri.nuri_server.domain.chat.domain.exception.RoomNotFoundException;
import nuri.nuri_server.domain.chat.domain.exception.UserRoomNotFoundException;
import nuri.nuri_server.domain.chat.domain.repository.ChatRecordRepository;
import nuri.nuri_server.domain.chat.domain.repository.RoomRepository;
import nuri.nuri_server.domain.chat.domain.repository.UserRoomAdapterEntityRepository;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomCreateRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.req.RoomInviteRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.req.UserExitRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.req.UserJoinRequestDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomCreateResponseDto;
import nuri.nuri_server.domain.chat.presentation.dto.res.RoomReadResponseDto;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.global.properties.ChatProperties;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
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
    private final ChatProperties chatProperties;
    private final KafkaTemplate<String, UserJoinRequestDto> kafkaJoinTemplate;
    private final KafkaTemplate<String, UserExitRequestDto> kafkaExitTemplate;

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
    public void exitRoom(NuriUserDetails nuriUserDetails, String roomId) {
        userRoomAdapterEntityRepository.updateLastReadAtByRoomIdAndUserId(UUID.fromString(roomId), nuriUserDetails.getName(), OffsetDateTime.now());
    }

    @Transactional
    public RoomCreateResponseDto createRoom(NuriUserDetails nuriUserDetails, RoomCreateRequestDto roomCreateRequestDto) {
        RoomEntity room = RoomEntity.builder()
                .name(roomCreateRequestDto.roomDto().name())
                .profile(roomCreateRequestDto.roomDto().profile())
                .isTeam(roomCreateRequestDto.isTeam())
                .build();
        roomRepository.save(room);
        boolean globalInvitePermission = !roomCreateRequestDto.isTeam();

        for(String userId : roomCreateRequestDto.users()) {
            boolean personalInvitePermission = nuriUserDetails.getName().equals(userId);
            UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
            userRoomAdapterEntityRepository.save(UserRoomAdapterEntity.builder()
                    .room(room)
                    .user(user)
                    .invitePermission(globalInvitePermission || personalInvitePermission)
                    .build()
            );
        }

        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .participantNumber(roomCreateRequestDto.users().size())
                .roomId(room.getId())
                .build();

        kafkaJoinTemplate.send("user-join", userJoinRequestDto);

        return RoomCreateResponseDto.builder()
                .id(room.getId())
                .users(roomCreateRequestDto.users())
                .room(roomCreateRequestDto.roomDto())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<RoomReadResponseDto> getRooms(NuriUserDetails nuriUserDetails, Pageable pageable) {
        List<RoomEntity> rooms = userRoomAdapterEntityRepository.findRoomsByUserId(nuriUserDetails.getName());
        List<String> roomIds = rooms.stream()
                .map(room -> room.getId().toString())
                .toList();
        Page<ChatRecord> latestChatRecords = chatRecordRepository.findLatestMessagesByRoomIds(roomIds, pageable);

        List<String> roomIdsInPage = latestChatRecords.stream()
                .map(ChatRecord::getRoomId)
                .collect(Collectors.toList());

        List<UserRoomAdapterEntity> userRoomAdapterEntities = userRoomAdapterEntityRepository.findByUserIdAndRoomIds(nuriUserDetails.getName(), roomIdsInPage);

        Map<String, OffsetDateTime> offsetDateTimeMap = userRoomAdapterEntities.stream()
                .collect(Collectors.toMap(
                        entity -> entity.getRoom().getId().toString(),
                        UserRoomAdapterEntity::getLastReadAt
                ));

        Map<String, ChatRecord> latestMessageMap = latestChatRecords.stream()
                .collect(Collectors.toMap(ChatRecord::getRoomId, Function.identity()));

        List<RoomReadResponseDto> roomReadResponseDtoList = rooms.stream()
                .sorted(Comparator.comparing(
                        room -> latestMessageMap.get(room.getId().toString()) == null,
                        Comparator.reverseOrder()))
                .map(room -> {
                    String roomId = room.getId().toString();
                    ChatRecord latestMessage = latestMessageMap.get(roomId);
                    OffsetDateTime offsetDateTime = offsetDateTimeMap.get(roomId);
                    long newMessageCount = chatRecordRepository.countByRoomIdAndCreatedAtAfter(roomId, offsetDateTime);
                    return RoomReadResponseDto.from(latestMessage, newMessageCount, room);
                })
                .toList();

        return new PageImpl<>(roomReadResponseDtoList, pageable, rooms.size());
    }

    @Transactional(readOnly = true)
    public List<String> getRoomsGroupChat(NuriUserDetails nuriUserDetails) {
        List<UUID> rooms = userRoomAdapterEntityRepository.findGroupRoomsByUserId(nuriUserDetails.getName(), chatProperties.getBroadcastThreshold());
        return rooms.stream().map(UUID::toString).toList();
    }

    @Transactional
    public void invite(NuriUserDetails nuriUserDetails, RoomInviteRequestDto roomInviteRequestDto) {
        if(userRoomAdapterEntityRepository.findInvitePermissionByRoomIdAndUserId(UUID.fromString(roomInviteRequestDto.roomId()), nuriUserDetails.getName())) {
            throw new UnauthorizedInvitationException();
        }

        UUID roomId = UUID.fromString(roomInviteRequestDto.roomId());

        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomInviteRequestDto.roomId()));

        for(String userId : roomInviteRequestDto.users()) {
            UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
            userRoomAdapterEntityRepository.save(UserRoomAdapterEntity.builder()
                    .room(roomEntity)
                    .user(user)
                    .invitePermission(!roomEntity.getIsTeam())
                    .build()
            );
        }

        Integer participantNumber = userRoomAdapterEntityRepository.countByRoom(roomEntity);

        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .participantNumber(participantNumber)
                .roomId(roomEntity.getId())
                .build();

        kafkaJoinTemplate.send("user-join", userJoinRequestDto);
    }

    @Transactional
    public void exit(NuriUserDetails nuriUserDetails, String roomId) {
        UUID roomUUID = UUID.fromString(roomId);

        int deletedCount = userRoomAdapterEntityRepository.deleteByRoomIdAndUserId(roomUUID, nuriUserDetails.getName());

        if (deletedCount == 0) {
            throw new UserRoomNotFoundException(roomId, nuriUserDetails.getName());
        }

        Integer participantNumber = userRoomAdapterEntityRepository.countByRoomId(roomUUID);

        UserExitRequestDto userExitRequestDto = UserExitRequestDto.builder()
                .participantNumber(participantNumber)
                .roomId(roomUUID)
                .userId(nuriUserDetails.getName())
                .build();

        kafkaExitTemplate.send("user-exit", userExitRequestDto);
    }

    @Transactional
    public void kick(NuriUserDetails nuriUserDetails, String roomId, String userId) {
        UUID roomUUID = UUID.fromString(roomId);
        if(userRoomAdapterEntityRepository.findInvitePermissionByRoomIdAndUserId(roomUUID, nuriUserDetails.getName())) {
            throw new UnauthorizedInvitationException();
        }

        int deletedCount = userRoomAdapterEntityRepository.deleteByRoomIdAndUserId(roomUUID, userId);

        if (deletedCount == 0) {
            throw new UserRoomNotFoundException(roomId, userId);
        }

        Integer participantNumber = userRoomAdapterEntityRepository.countByRoomId(roomUUID);

        UserExitRequestDto userExitRequestDto = UserExitRequestDto.builder()
                .participantNumber(participantNumber)
                .roomId(roomUUID)
                .userId(userId)
                .build();

        kafkaExitTemplate.send("user-exit", userExitRequestDto);
    }
}
