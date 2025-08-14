package nuri.nuri_server.domain.post.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.application.service.PostCommentService;
import nuri.nuri_server.domain.post.presentation.dto.common.PostCommentDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostCommentListReadRequestDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostCommentCreateRequestDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostCommentUpdateRequestDto;
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
public class PostCommentController {

    private final PostCommentService postCommentService;

    @User
    @MutationMapping
    public String createPostComment(
            @Argument("postCommentCreateInput") @Valid PostCommentCreateRequestDto postCommentCreateRequestDto,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        postCommentService.createComment(postCommentCreateRequestDto, nuriUserDetails);
        return "게시물 댓글을 작성하였습니다.";
    }

    @QueryMapping
    public List<PostCommentDto> getPostCommentList(
            @Argument("postCommentListReadInput") @Valid PostCommentListReadRequestDto postCommentListReadRequestDto
    ) {
        return postCommentService.getCommentList(postCommentListReadRequestDto);
    }

    @User
    @MutationMapping
    public String updatePostComment(
            @Argument("postCommentUpdateInput") @Valid PostCommentUpdateRequestDto postCommentUpdateRequestDto,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        postCommentService.updateComment(postCommentUpdateRequestDto, nuriUserDetails);
        return "게시물 댓글을 수정하였습니다.";
    }

    @User
    @MutationMapping
    public String deletePostComment(
            @Argument("commentId") @NotNull(message = "댓글 아이디(commentId)는 필수 항목입니다.") UUID commentId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        postCommentService.deleteComment(commentId, nuriUserDetails);
        return "게시물 댓글을 삭제하였습니다.";
    }
}
