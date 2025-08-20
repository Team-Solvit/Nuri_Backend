package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteBoardingHouseService {

    private final BoardingRoomRepository boardingRoomRepository;

    @Transactional
    public void deleteBoardingRoom(NuriUserDetails nuriUserDetails, UUID roomId) {
        log.info("하숙방 삭제 요청: userId={}, roomId={}",
                nuriUserDetails.getId(), roomId);

        BoardingRoomEntity room = boardingRoomRepository.findById(roomId)
                .orElseThrow(BoardingRoomNotFoundException::new);

        room.validateHost(nuriUserDetails.getUser());

        boardingRoomRepository.delete(room);
        log.info("하숙방 삭제 완료: roomId={}", roomId);
    }
}
