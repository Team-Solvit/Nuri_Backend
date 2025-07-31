package nuri.nuri_server.domain.post.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.application.service.CreatePostService;
import nuri.nuri_server.domain.post.application.service.DeletePostService;
import nuri.nuri_server.domain.post.application.service.GetPostService;
import nuri.nuri_server.domain.post.application.service.UpdatePostService;
import nuri.nuri_server.domain.post.presentation.dto.request.CreatePostRequest;
import nuri.nuri_server.domain.post.presentation.dto.request.UpdatePostRequest;
import nuri.nuri_server.domain.post.presentation.dto.response.GetPostListResponse;
import nuri.nuri_server.global.security.annotation.User;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final CreatePostService createPostService;
    private final UpdatePostService updatePostService;
    private final GetPostService getPostService;
    private final DeletePostService deletePostService;

    @User
    @MutationMapping
    public String createPost(
            @Argument("createPostInput") @Valid CreatePostRequest createPostRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        createPostService.createPost(createPostRequest, nuriUserDetails);
        return "게시물을 생성하였습니다.";
    }

    @QueryMapping
    public List<GetPostListResponse> getPostList(
            @Argument("start") @NotNull(message = "게시물 리스트 조회시 시작값(start)은 필수 항목입니다.") Integer start,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return getPostService.getPostList(start, nuriUserDetails);
    }

    @User
    @MutationMapping
    public String updatePost(
            @Argument("updatePostInput") @Valid UpdatePostRequest updatePostRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        updatePostService.updatePost(updatePostRequest, nuriUserDetails);
        return "게시물을 수정하였습니다.";
    }

    @User
    @MutationMapping
    public String deletePost(
            @Argument("postId") @NotNull(message = "게시물 삭제시 게시물 아이디(postId)는 필수 항목입니다.") UUID postId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        deletePostService.deletePost(postId, nuriUserDetails);
        return "게시물을 삭제하였습니다.";
    }
}
