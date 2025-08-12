package nuri.nuri_server.domain.boarding_house.application.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomLikeEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomLikeRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardingRoomLikeService {

    private final BoardingRoomRepository boardingRoomRepository;
    private final BoardingRoomLikeRepository boardingRoomLikeRepository;

    @Transactional
    public void like(UUID roomId, NuriUserDetails nuriUserDetails) {
        log.info("하숙방 좋아요 요청: userId={}, roomId={}", nuriUserDetails.getId(), roomId);

        BoardingRoomEntity room = boardingRoomRepository.findById(roomId)
                .orElseThrow(BoardingRoomNotFoundException::new);

        UserEntity user = nuriUserDetails.getUser();

        if(boardingRoomLikeRepository.existsByBoardingRoomIdAndUserId(roomId, user.getId())) {
            log.debug("하숙방 좋아요를 이미 생성한 유저입니다. : userId={}, roomId={}", user.getId(), roomId);
            return;
        }

        BoardingRoomLikeEntity likeEntity = BoardingRoomLikeEntity.builder()
                .boardingRoom(room)
                .user(user)
                .build();

        boardingRoomLikeRepository.save(likeEntity);

        log.info("하숙방 좋아요 생성 완료 : userId={}, roomId={}", user.getId(), roomId);
    }

    @Transactional
    public void unlike(UUID roomId, NuriUserDetails nuriUserDetails) {
        UUID userId = nuriUserDetails.getId();
        log.info("하숙방 좋아요 취소 요청: userId={}, roomId={}", roomId, roomId);

        Integer deleteCount = boardingRoomLikeRepository.deleteByBoardingRoomIdAndUserId(roomId, userId);

        if(deleteCount.equals(0)) {
            log.debug("하숙방 좋아요 기록이 없어 삭제할 수 없습니다 : userId={}, roomId={}", userId, roomId);
            return;
        }

        log.info("하숙방 좋아요 취소 완료 : userId={}, roomId={}", userId, roomId);
    }
}
