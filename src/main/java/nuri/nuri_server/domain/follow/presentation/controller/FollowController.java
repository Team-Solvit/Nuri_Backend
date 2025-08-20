package nuri.nuri_server.domain.follow.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.follow.application.service.FollowService;
import nuri.nuri_server.domain.follow.presentation.dto.res.FollowUserInfoResponseDto;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @QueryMapping
    public List<FollowUserInfoResponseDto> getFollowerInfo(@Argument @NotNull(message="유저 아이디 값은 존재해야 합니다.") String userId) {
        return followService.getFollowerInfo(userId);
    }

    @QueryMapping
    public List<FollowUserInfoResponseDto> getFollowingInfo(@Argument @NotNull(message="유저 아이디 값은 존재해야 합니다.") String userId) {
        return followService.getFollowingInfo(userId);
    }

    @MutationMapping
    public void follow(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message="유저 아이디 값은 존재해야 합니다.") String userId) {
        followService.follow(nuriUserDetails, userId);
    }

    @MutationMapping
    public void unfollow(@AuthenticationPrincipal NuriUserDetails nuriUserDetails, @Argument @NotNull(message="유저 아이디 값은 존재해야 합니다.") String userId) {
        followService.unfollow(nuriUserDetails, userId);
    }
}
