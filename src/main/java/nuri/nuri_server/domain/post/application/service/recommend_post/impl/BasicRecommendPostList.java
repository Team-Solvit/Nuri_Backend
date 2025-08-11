package nuri.nuri_server.domain.post.application.service.recommend_post.impl;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomQueryService;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomCommentRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomLikeRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomInfo;
import nuri.nuri_server.domain.post.application.service.SnsPostQueryService;
import nuri.nuri_server.domain.post.application.service.recommend_post.RecommendPostList;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.repository.*;
import nuri.nuri_server.domain.post.presentation.dto.*;
import nuri.nuri_server.domain.post.presentation.dto.response.*;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class BasicRecommendPostList implements RecommendPostList {
    private final PostRepository postRepository;
    private final BoardingRoomRepository boardingRoomRepository;
    private final BoardingRoomLikeRepository boardingRoomLikeRepository;
    private final BoardingRoomCommentRepository boardingRoomCommentRepository;
    private final BoardingRoomQueryService boardingRoomQueryService;
    private final SnsPostQueryService snsPostQueryService;
    private final Integer snsSize = 15;
    private final Integer boardingSize = 5;

    @Override
    public List<GetPostListResponse> getRecommendSnsPostList(Integer start, NuriUserDetails nuriUserDetails) {
        Pageable pageable = PageRequest.of(start, snsSize, Sort.by("updatedAt").descending());
        Page<PostEntity> pagePostEntities = postRepository.findAll(pageable);
        return pagePostEntities.getContent().stream()
                .map(this::setSnsPostResponse)
                .toList();
    }

    private GetPostListResponse setSnsPostResponse(PostEntity post) {
        SnsPostInfo snsPostInfo = snsPostQueryService.getSnsPost(post);

        return GetPostListResponse.builder()
                .postType(PostType.SNS)
                .postInfo(snsPostInfo)
                .build();
    }

    @Override
    public List<GetPostListResponse> getRecommendBoardingPostList(Integer start, NuriUserDetails nuriUserDetails) {
        Pageable pageable = PageRequest.of(start, boardingSize, Sort.by("updatedAt").descending());
        Page<BoardingRoomEntity> pageBoardingPostEntities = boardingRoomRepository.findAll(pageable);
        return pageBoardingPostEntities.getContent().stream()
                .map(this::setBoardingPostResponse)
                .toList();
    }

    private GetPostListResponse setBoardingPostResponse(BoardingRoomEntity boardingRoom) {
        Long likeCount = boardingRoomLikeRepository.countByBoardingRoomId(boardingRoom.getId());
        Long commentCount = boardingRoomCommentRepository.countByBoardingRoomId(boardingRoom.getId());

        BoardingRoomInfo room = boardingRoomQueryService.getBoardingRoomInfo(boardingRoom);

        BoardingPostInfo boardingPostInfo = BoardingPostInfo.builder()
                .room(room)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();

        return GetPostListResponse.builder()
                .postType(PostType.BOARDING)
                .postInfo(boardingPostInfo)
                .build();
    }
}
