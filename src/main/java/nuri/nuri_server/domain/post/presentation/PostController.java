package nuri.nuri_server.domain.post.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.application.service.PostService;
import nuri.nuri_server.domain.post.presentation.dto.request.CreatePostRequest;
import nuri.nuri_server.global.security.annotation.User;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @User
    @MutationMapping
    public String createPost(
            @Argument("createPostInput") @Valid CreatePostRequest createPostRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        postService.createPost(createPostRequest, nuriUserDetails);
        return "게시물을 생성하였습니다.";
    }
}
