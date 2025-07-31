package nuri.nuri_server.domain.post.application.service.recommend_post.impl;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomFileEntity;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomCommentRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomFileRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomLikeRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.post.application.service.recommend_post.RecommendPostList;
import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;
import nuri.nuri_server.domain.post.domain.repository.*;
import nuri.nuri_server.domain.post.presentation.dto.response.*;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
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
    private final PostFileRepository postFileRepository;
    private final HashTagRepository hashTagRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final BoardingRoomRepository boardingRoomRepository;
    private final BoardingRoomFileRepository boardingRoomFileRepository;
    private final BoardingRoomLikeRepository boardingRoomLikeRepository;
    private final BoardingRoomCommentRepository boardingRoomCommentRepository;
    private final UserRepository userRepository;
    private final Integer snsSize = 15;
    private final Integer boardingSize = 5;

    @Override
    public List<GetPostListResponse> getRecommendSnsPostList(Integer start, UserEntity userEntity) {
        Pageable pageable = PageRequest.of(start, snsSize, Sort.by("updatedAt").descending());
        Page<PostEntity> pagePostEntities = postRepository.findAll(pageable);
        return pagePostEntities.getContent().stream()
                .map(this::setSnsPostResponse)
                .toList();
    }

    private GetPostListResponse setSnsPostResponse(PostEntity post) {
        List<String> fileUrls = getFileUrls(post);
        List<String> hashTags = getHashTags(post);
        Long likes = postLikeRepository.countByPostId(post.getId());
        Long comments = commentRepository.countByPostId(post.getId());
        UserEntity user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(post.getUser().getUserId()));

        SnsPost snsPost = SnsPost.builder()
                .type(PostType.SNS)
                .postId(post.getId().toString())
                .files(fileUrls)
                .title(post.getTitle())
                .contents(post.getContents())
                .likes(likes)
                .comments(comments)
                .day(post.getUpdatedAt().toLocalDate())
                .hashtags(hashTags)
                .build();

        AuthorInfo author = AuthorInfo.builder()
                .authorId(user.getId().toString())
                .profile(user.getProfile())
                .name(user.getName())
                .build();

        return GetPostListResponse.builder()
                .post(snsPost)
                .author(author)
                .build();
    }

    private List<String> getFileUrls(PostEntity post) {
        return postFileRepository.findAllByPostId(post.getId()).stream()
                .map(PostFileEntity::getMediaUrl)
                .toList();
    }

    private List<String> getFileUrls(BoardingRoomEntity boardingRoom) {
        return boardingRoomFileRepository.findAllByBoardingRoomId(boardingRoom.getId()).stream()
                .map(BoardingRoomFileEntity::getMediaUrl)
                .toList();
    }

    private List<String> getHashTags(PostEntity post) {
        return hashTagRepository.findAllByPostId(post.getId()).stream()
                .map(HashTagEntity::getName)
                .toList();
    }

    @Override
    public List<GetPostListResponse> getRecommendBoardingPostList(Integer start, UserEntity userEntity) {
        Pageable pageable = PageRequest.of(start, boardingSize, Sort.by("updatedAt").descending());
        Page<BoardingRoomEntity> pageBoardingPostEntities = boardingRoomRepository.findAll(pageable);
        return pageBoardingPostEntities.getContent().stream()
                .map(this::setBoardingPostResponse)
                .toList();
    }

    private GetPostListResponse setBoardingPostResponse(BoardingRoomEntity boardingRoom) {
        List<String> fileUrls = getFileUrls(boardingRoom);
        Long likes = boardingRoomLikeRepository.countByBoardingRoomId(boardingRoom.getId());
        Long comments = boardingRoomCommentRepository.countByBoardingRoomId(boardingRoom.getId());
        UserEntity user = userRepository.findById(boardingRoom.getBoardingHouse().getHost().getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(boardingRoom.getBoardingHouse().getHost().getUser().getUserId()));

        BoardingPost boardingPost = BoardingPost.builder()
                .type(PostType.BOARDING)
                .postId(boardingRoom.getId().toString())
                .files(fileUrls)
                .title(boardingRoom.getName())
                .contents(boardingRoom.getDescription())
                .likes(likes)
                .comments(comments)
                .price(boardingRoom.getMonthlyRent())
                .day(boardingRoom.getUpdatedAt().toLocalDate())
                .build();

        AuthorInfo author = AuthorInfo.builder()
                .authorId(user.getId().toString())
                .profile(user.getProfile())
                .name(user.getName())
                .build();

        return GetPostListResponse.builder()
                .post(boardingPost)
                .author(author)
                .build();
    }
}
