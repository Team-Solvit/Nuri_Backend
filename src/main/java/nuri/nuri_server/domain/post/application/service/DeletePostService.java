package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.exception.PostNotFoundException;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeletePostService {
    private final PostRepository postRepository;

    public void deletePost(UUID postId, NuriUserDetails nuriUserDetails) {
        log.info("게시물 삭제 요청: userId={}, postId={}",
                nuriUserDetails.user().getId(), postId);

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.validateAuthor(nuriUserDetails.user());

        postRepository.deleteById(postId);
        log.info("게시물 삭제 완료: postId={}", postId);
    }
}
