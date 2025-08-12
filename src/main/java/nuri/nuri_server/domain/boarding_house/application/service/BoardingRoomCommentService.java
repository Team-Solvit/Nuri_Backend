package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomCommentEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomCommentRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomCommentInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.CreateBoardingRoomCommentRequest;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.GetBoardingRoomCommentListRequest;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardingRoomCommentService {
    private final BoardingRoomCommentRepository boardingRoomCommentRepository;
    private final BoardingRoomRepository boardingRoomRepository;

    @Value("${page-size.comment}")
    private Integer size;

    @Transactional
    public void createComment(NuriUserDetails nuriUserDetails, CreateBoardingRoomCommentRequest createBoardingRoomCommentRequest) {
        UserEntity user = nuriUserDetails.getUser();
        log.info("하숙방 댓글 작성 요청 : userId={}, roomId={}", user.getId() , createBoardingRoomCommentRequest.roomId());

        BoardingRoomEntity room = boardingRoomRepository.findById(createBoardingRoomCommentRequest.roomId())
                .orElseThrow(BoardingRoomNotFoundException::new);

        BoardingRoomCommentEntity comment = BoardingRoomCommentEntity.builder()
                .boardingRoom(room)
                .user(user)
                .contents(createBoardingRoomCommentRequest.contents())
                .build();

        boardingRoomCommentRepository.save(comment);
        log.info("하숙방 댓글 작성 완료 : userId={}, roomId={}", user.getId() , createBoardingRoomCommentRequest.roomId());
    }

    @Transactional(readOnly = true)
    public List<BoardingRoomCommentInfo> getCommentList(GetBoardingRoomCommentListRequest getBoardingRoomCommentListRequest) {
        log.info("댓글 리스트 조회 요청 : roomId={}", getBoardingRoomCommentListRequest.roomId());
        Pageable pageable = PageRequest.of(getBoardingRoomCommentListRequest.start(), size, Sort.by("updatedAt").descending());
        Page<BoardingRoomCommentEntity> pageCommentEntities = boardingRoomCommentRepository.findAllByBoardingRoomId(getBoardingRoomCommentListRequest.roomId(), pageable);

        List<BoardingRoomCommentInfo> results = pageCommentEntities.getContent().stream()
                .map(BoardingRoomCommentInfo::from)
                .toList();

        log.info("하숙방 댓글 리스트 조회 완료 : commentCount={}", results.size());
        return results;
    }
}
