package nuri.nuri_server.domain.post.application.service.recommend_post;

import nuri.nuri_server.domain.post.presentation.dto.response.GetPostListResponse;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;

import java.util.List;

public interface RecommendPostList {
    List<GetPostListResponse> getRecommendSnsPostList(Integer start, UserEntity userEntity);
    List<GetPostListResponse> getRecommendBoardingPostList(Integer start, UserEntity userEntity);
}