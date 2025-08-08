package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.application.service.recommend_post.RecommendPostList;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;
import nuri.nuri_server.domain.post.domain.exception.PostNotFoundException;
import nuri.nuri_server.domain.post.domain.repository.*;
import nuri.nuri_server.domain.post.presentation.dto.response.GetPostThumbnailResponse;
import nuri.nuri_server.domain.post.presentation.dto.SnsPostInfo;
import nuri.nuri_server.domain.post.presentation.dto.request.GetUserPostListRequest;
import nuri.nuri_server.domain.post.presentation.dto.response.GetPostListResponse;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetPostService {
    private final RecommendPostList recommendPostList;
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final SnsPostQueryService snsPostQueryService;
    private final Integer userPostSize = 20;

    @Transactional(readOnly = true)
    public List<GetPostListResponse> getPostList(Integer start, NuriUserDetails nuriUserDetails) {
        log.info("게시물 리스트 요청: start={}", start);

        List<GetPostListResponse> snsPosts = recommendPostList.getRecommendSnsPostList(start, nuriUserDetails);
        List<GetPostListResponse> boardingPosts = recommendPostList.getRecommendBoardingPostList(start, nuriUserDetails);

        log.debug("게시물 추천 결과: snsPostCount={}, boardingPostCount={}",
                snsPosts.size(), boardingPosts.size());

        int boardingIndex = 0;
        List<GetPostListResponse> results = new ArrayList<>();

        for(int i = 0; i < snsPosts.size(); i++) {
            results.add(snsPosts.get(i));
            if((i + 1) % 3 == 0 && boardingIndex < boardingPosts.size()) {
                results.add(boardingPosts.get(boardingIndex++));
            }
        }

        while(boardingIndex < boardingPosts.size()) {
            results.add(boardingPosts.get(boardingIndex++));
        }

        log.info("게시물 리스트 응답: total={}", results.size());
        return results;
    }

    @Transactional(readOnly = true)
    public List<GetPostThumbnailResponse> getUserPostList(GetUserPostListRequest getUserPostListRequest) {
        log.info("유저 게시물 리스트 요청: userId={}", getUserPostListRequest.userId());

        Pageable pageable = PageRequest.of(getUserPostListRequest.start(), userPostSize, Sort.by("updatedAt").descending());
        Page<PostEntity> pagePostEntities = postRepository.findAllByUserId(getUserPostListRequest.userId(), pageable);

        List<GetPostThumbnailResponse> results = pagePostEntities.getContent().stream()
                .map(this::setPostThumbnail)
                .toList();

        log.info("유저 게시물 리스트 조회 완료 : postThumbnailInfoCount={}", results.size());
        return results;
    }

    private GetPostThumbnailResponse setPostThumbnail(PostEntity post) {
        PostFileEntity postFile = postFileRepository.findFirstByPostIdOrderByCreatedAtAsc(post.getId())
                .orElseThrow(PostNotFoundException::new);

        return GetPostThumbnailResponse.builder()
                .postId(post.getId())
                .thumbnail(postFile.getMediaUrl())
                .build();
    }

    @Transactional(readOnly = true)
    public SnsPostInfo getPost(UUID postId) {
        log.info("게시물 자세히 보기 요청: postId={}", postId);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        SnsPostInfo snsPostInfo = snsPostQueryService.getSnsPost(post);

        log.info("게시물 자세히 보기 완료 : post={}", snsPostInfo.toString());
        return snsPostInfo;
    }
}
