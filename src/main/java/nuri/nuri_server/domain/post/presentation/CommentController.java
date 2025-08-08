package nuri.nuri_server.domain.post.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.application.service.CommentService;
import nuri.nuri_server.domain.post.presentation.dto.CommentInfo;
import nuri.nuri_server.domain.post.presentation.dto.request.GetCommentListRequest;
import nuri.nuri_server.domain.post.presentation.dto.request.CreatePostCommentRequest;
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
public class CommentController {

    private final CommentService commentService;

    @User
    @MutationMapping
    public String createPostComment(
            @Argument("createPostCommentInput") @Valid CreatePostCommentRequest createPostCommentRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        commentService.createComment(createPostCommentRequest, nuriUserDetails);
        return "게시물 댓글을 작성하였습니다.";
    }

    @QueryMapping
    public List<CommentInfo> getPostCommentList(
            @Argument("getPostCommentListInput") @Valid GetCommentListRequest getCommentListRequest
    ) {
        return commentService.getCommentList(getCommentListRequest);
    }

    @User
    @MutationMapping
    public String updatePostComment(
            @Argument("updatePostCommentInput") @Valid CommentInfo commentInfo,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        commentService.updateComment(commentInfo, nuriUserDetails);
        return "게시물 댓글을 수정하였습니다.";
    }

    @User
    @MutationMapping
    public String deletePostComment(
            @Argument("commentId") @NotNull(message = "댓글 아이디(commentId)는 필수 항목입니다.") UUID commentId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        commentService.deleteComment(commentId, nuriUserDetails);
        return "게시물 댓글을 삭제하였습니다.";
    }
}
