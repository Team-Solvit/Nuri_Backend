package nuri.nuri_server.domain.post.application.service.recommend_post;

import nuri.nuri_server.domain.post.presentation.dto.response.GetPostListResponse;
import nuri.nuri_server.global.security.user.NuriUserDetails;

import java.util.List;

public interface RecommendPostList {
    List<GetPostListResponse> getRecommendSnsPostList(Integer start, NuriUserDetails nuriUserDetails);
    List<GetPostListResponse> getRecommendBoardingPostList(Integer start, NuriUserDetails nuriUserDetails);
}