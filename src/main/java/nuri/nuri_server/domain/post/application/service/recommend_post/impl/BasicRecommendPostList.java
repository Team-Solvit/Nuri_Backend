package nuri.nuri_server.domain.post.application.service.recommend_post.impl;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomQueryService;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomCommentRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomLikeRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.post.application.service.SnsPostQueryService;
import nuri.nuri_server.domain.post.application.service.recommend_post.RecommendPostList;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.repository.*;
import nuri.nuri_server.domain.post.presentation.dto.common.BoardingPost;
import nuri.nuri_server.domain.post.presentation.dto.common.PostType;
import nuri.nuri_server.domain.post.presentation.dto.common.SnsPost;
import nuri.nuri_server.domain.post.presentation.dto.res.*;
import nuri.nuri_server.global.properties.PageSizeProperties;
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
    private final PageSizeProperties pageSizeProperties;

    @Override
    public List<PostListReadResponseDto> getRecommendSnsPostList(Integer start, NuriUserDetails nuriUserDetails) {
        Integer size = pageSizeProperties.getSnsPost();
        Pageable pageable = PageRequest.of(start, size, Sort.by("updatedAt").descending());
        Page<PostEntity> pagePostEntities = postRepository.findAll(pageable);
        return pagePostEntities.getContent().stream()
                .map(this::buildSnsPostResponse)
                .toList();
    }

    private PostListReadResponseDto buildSnsPostResponse(PostEntity post) {
        SnsPost snsPostInfo = snsPostQueryService.getSnsPost(post);

        return PostListReadResponseDto.builder()
                .postType(PostType.SNS)
                .postInfo(snsPostInfo)
                .build();
    }

    @Override
    public List<PostListReadResponseDto> getRecommendBoardingPostList(Integer start, NuriUserDetails nuriUserDetails) {
        Integer size = pageSizeProperties.getBoardingPost();
        Pageable pageable = PageRequest.of(start, size, Sort.by("updatedAt").descending());
        Page<BoardingRoomEntity> pageBoardingPostEntities = boardingRoomRepository.findAll(pageable);
        return pageBoardingPostEntities.getContent().stream()
                .map(this::buildBoardingPostResponse)
                .toList();
    }

    private PostListReadResponseDto buildBoardingPostResponse(BoardingRoomEntity boardingRoom) {
        Long likeCount = boardingRoomLikeRepository.countByBoardingRoomId(boardingRoom.getId());
        Long commentCount = boardingRoomCommentRepository.countByBoardingRoomId(boardingRoom.getId());

        BoardingRoomDto room = boardingRoomQueryService.getBoardingRoomDto(boardingRoom);

        BoardingPost boardingPost = BoardingPost.builder()
                .room(room)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();

        return PostListReadResponseDto.builder()
                .postType(PostType.BOARDING)
                .postInfo(boardingPost)
                .build();
    }
}
