package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.application.service.recommend_post.RecommendPostList;
import nuri.nuri_server.domain.post.presentation.dto.response.GetPostListResponse;
import nuri.nuri_server.domain.post.presentation.dto.response.PostListInfo;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetPostService {
    private final RecommendPostList recommendPostList;

    @Transactional
    public List<GetPostListResponse> getPostList(Integer start, NuriUserDetails nuriUserDetails) {
        log.info("게시물 리스트 요청: userId={}, start={}", nuriUserDetails.getUser().getId(), start);

        List<GetPostListResponse> snsPosts = recommendPostList.getRecommendSnsPostList(start, nuriUserDetails.getUser());
        List<GetPostListResponse> boardingPosts = recommendPostList.getRecommendBoardingPostList(start, nuriUserDetails.getUser());

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

        log.info("게시물 리스트 응답: userId={}, total={}",
                nuriUserDetails.getUser().getId(), results.size());
        return results;
    }
}
