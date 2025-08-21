package nuri.nuri_server.domain.post.application.service.recommend_post;

import nuri.nuri_server.domain.post.presentation.dto.res.PostListReadResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;

import java.util.List;

public interface RecommendPostList {
    List<PostListReadResponseDto> getRecommendSnsPostList(Integer start, NuriUserDetails nuriUserDetails);
    List<PostListReadResponseDto> getRecommendBoardingPostList(Integer start, NuriUserDetails nuriUserDetails);
}