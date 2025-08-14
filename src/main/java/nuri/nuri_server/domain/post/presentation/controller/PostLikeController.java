package nuri.nuri_server.domain.post.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.application.service.PostLikeService;
import nuri.nuri_server.global.security.annotation.User;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @User
    @MutationMapping
    public String likePost(
            @Argument("postId") @NotNull(message = "게시물 좋아요 생성시 게시물 아이디(postId)는 필수 항목입니다.") UUID postId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        postLikeService.like(postId, nuriUserDetails);
        return "게시물 좋아요를 눌렀습니다.";
    }

    @User
    @MutationMapping
    public String unlikePost(
            @Argument("postId") @NotNull(message = "게시물 좋아요 취소시 게시물 아이디(postId)는 필수 항목입니다.") UUID postId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        postLikeService.unlike(postId, nuriUserDetails);
        return "게시물 좋아요를 취소하였습니다.";
    }
}
