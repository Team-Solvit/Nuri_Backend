package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.entity.PostLikeEntity;
import nuri.nuri_server.domain.post.domain.exception.PostNotFoundException;
import nuri.nuri_server.domain.post.domain.repository.PostLikeRepository;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void like(UUID postId, NuriUserDetails nuriUserDetails) {
        log.info("게시물 좋아요 요청: userId={}, postId={}", nuriUserDetails.getUser().getId(), postId);

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        UserEntity user = nuriUserDetails.getUser();

        if(postLikeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            log.debug("게시물 좋아요를 이미 생성한 유저입니다. : userId={}, postId={}", user.getId(), postId);
            return;
        }

        PostLikeEntity likeEntity = PostLikeEntity.builder()
                .post(post)
                .user(user)
                .build();

        postLikeRepository.save(likeEntity);

        log.info("게시물 좋아요 생성 완료 : userId={}, postId={}", user.getId(), postId);
    }

    @Transactional
    public void unlike(UUID postId, NuriUserDetails nuriUserDetails) {
        UUID userId = nuriUserDetails.getId();
        log.info("게시물 좋아요 취소 요청: userId={}, postId={}", userId, postId);

        Integer deleteCount = postLikeRepository.deleteByPostIdAndUserId(postId, userId);

        if(deleteCount.equals(0)) {
            log.debug("게시물 좋아요 기록이 없어 삭제할 수 없습니다 : userId={}, postId={}", userId, postId);
            return;
        }

        log.info("게시물 좋아요 취소 완료 : userId={}, postId={}", userId, postId);
    }
}
