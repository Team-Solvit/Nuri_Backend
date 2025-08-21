package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.PostCommentEntity;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.exception.CommentNotFoundException;
import nuri.nuri_server.domain.post.domain.exception.PostNotFoundException;
import nuri.nuri_server.domain.post.domain.repository.PostCommentRepository;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.post.presentation.dto.common.PostCommentDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostCommentListReadRequestDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostCommentCreateRequestDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostCommentUpdateRequestDto;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.properties.PageSizeProperties;
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
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final PageSizeProperties pageSizeProperties;

    @Transactional
    public void createComment(PostCommentCreateRequestDto postCommentCreateRequestDto, NuriUserDetails nuriUserDetails) {
        UserEntity user = nuriUserDetails.user();
        log.info("게시물 댓글 작성 요청 : userId={}, postId={}", user.getId() , postCommentCreateRequestDto.postId());

        PostEntity post = postRepository.findById(postCommentCreateRequestDto.postId())
                .orElseThrow(PostNotFoundException::new);

        PostCommentEntity postCommentEntity = PostCommentEntity.builder()
                .post(post)
                .user(user)
                .contents(postCommentCreateRequestDto.contents())
                .build();

        postCommentRepository.save(postCommentEntity);
        log.info("게시물 댓글 작성 완료 : userId={}, postId={}", user.getId() , postCommentCreateRequestDto.postId());
    }

    @Transactional(readOnly = true)
    public List<PostCommentDto> getCommentList(PostCommentListReadRequestDto postCommentListReadRequestDto) {
        log.info("게시물 댓글 리스트 조회 요청 : postId={}", postCommentListReadRequestDto.postId());

        Integer size = pageSizeProperties.getComment();
        Pageable pageable = PageRequest.of(postCommentListReadRequestDto.start(), size, Sort.by("updatedAt").descending());
        Page<PostCommentEntity> pageCommentEntities = postCommentRepository.findAllByPostId(postCommentListReadRequestDto.postId(), pageable);

        List<PostCommentDto> results = pageCommentEntities.getContent().stream()
                .map(PostCommentDto::from)
                .toList();

        log.info("게시물 댓글 리스트 조회 완료 : commentCount={}", results.size());
        return results;
    }

    @Transactional
    public void updateComment(PostCommentUpdateRequestDto postCommentUpdateRequestDto, NuriUserDetails nuriUserDetails) {
        UserEntity user = nuriUserDetails.user();
        log.info("게시물 댓글 수정 요청 : userId={}, commentId={}", user.getId() , postCommentUpdateRequestDto.commentId());

        PostCommentEntity comment = postCommentRepository.findById(postCommentUpdateRequestDto.commentId())
                .orElseThrow(CommentNotFoundException::new);

        comment.validateCommenter(user);

        comment.edit(postCommentUpdateRequestDto.content());
        log.info("게시물 댓글 수정 완료 : commentId={}, content={}",  postCommentUpdateRequestDto.commentId(), comment.getContents());
    }

    @Transactional
    public void deleteComment(UUID commentId, NuriUserDetails nuriUserDetails) {
        UserEntity user = nuriUserDetails.user();
        log.info("게시물 댓글 삭제 요청 : userId={}, commentId={}", user.getId() , commentId);

        PostCommentEntity comment = postCommentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.validateCommenter(user);

        postCommentRepository.delete(comment);
        log.info("게시물 댓글 삭제 완료 : userId={}, commentId={}", user.getId() , commentId);
    }
}
