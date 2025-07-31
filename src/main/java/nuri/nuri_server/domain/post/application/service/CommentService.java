package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.CommentEntity;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.exception.CommentNotFoundException;
import nuri.nuri_server.domain.post.domain.exception.PostNotFoundException;
import nuri.nuri_server.domain.post.domain.repository.CommentRepository;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.post.presentation.dto.AuthorInfo;
import nuri.nuri_server.domain.post.presentation.dto.CommentInfo;
import nuri.nuri_server.domain.post.presentation.dto.request.GetCommentListRequest;
import nuri.nuri_server.domain.post.presentation.dto.request.CreatePostCommentRequest;
import nuri.nuri_server.domain.post.presentation.dto.response.GetCommentResponse;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final Integer size = 20;

    @Transactional
    public void createComment(CreatePostCommentRequest createPostCommentRequest, NuriUserDetails nuriUserDetails) {
        UserEntity user = nuriUserDetails.getUser();
        log.info("댓글 작성 요청 : userId={}, postId={}", user.getId() , createPostCommentRequest.postId());

        PostEntity post = postRepository.findById(createPostCommentRequest.postId())
                .orElseThrow(PostNotFoundException::new);

        CommentEntity commentEntity = CommentEntity.builder()
                .post(post)
                .user(user)
                .contents(createPostCommentRequest.contents())
                .build();

        commentRepository.save(commentEntity);
        log.info("댓글 작성 완료 : userId={}, postId={}", user.getId() , createPostCommentRequest.postId());
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getCommentList(GetCommentListRequest getCommentListRequest) {
        log.info("댓글 리스트 조회 요청 : postId={}", getCommentListRequest.postId());
        Pageable pageable = PageRequest.of(getCommentListRequest.start(), size, Sort.by("updatedAt").descending());
        Page<CommentEntity> pageCommentEntities = commentRepository.findAllByPostId(getCommentListRequest.postId(), pageable);

        List<GetCommentResponse> results = pageCommentEntities.getContent().stream()
                .map(this::setCommentResponse)
                .toList();

        log.info("댓글 리스트 조회 완료 : CommentCount={}", results.size());
        return results;
    }

    private GetCommentResponse setCommentResponse(CommentEntity commentEntity) {
        CommentInfo comment = CommentInfo.builder()
                .commentId(commentEntity.getId())
                .content(commentEntity.getContents())
                .build();
        UserEntity user = commentEntity.getUser();

        AuthorInfo commenter = AuthorInfo.builder()
                .authorId(user.getId())
                .name(user.getName())
                .profile(user.getProfile())
                .build();

        return GetCommentResponse.builder()
                .comment(comment)
                .commenter(commenter)
                .build();
    }

    @Transactional
    public void updateComment(CommentInfo commentInfo, NuriUserDetails nuriUserDetails) {
        UserEntity user = nuriUserDetails.getUser();
        log.info("댓글 수정 요청 : userId={}, commentId={}", user.getId() , commentInfo.commentId());

        CommentEntity comment = commentRepository.findById(commentInfo.commentId())
                .orElseThrow(CommentNotFoundException::new);

        comment.validateCommenter(user);

        comment.edit(commentInfo.content());
        log.info("댓글 수정 완료 : commentId={}, content={}",  commentInfo.commentId(), comment.getContents());
    }

    @Transactional
    public void deleteComment(UUID commentId, NuriUserDetails nuriUserDetails) {
        UserEntity user = nuriUserDetails.getUser();
        log.info("댓글 삭제 요청 : userId={}, commentId={}", user.getId() , commentId);

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.validateCommenter(user);

        commentRepository.delete(comment);
        log.info("댓글 삭제 완료 : userId={}, commentId={}", user.getId() , commentId);
    }
}
