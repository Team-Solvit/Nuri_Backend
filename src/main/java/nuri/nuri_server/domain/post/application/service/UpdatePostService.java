package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;
import nuri.nuri_server.domain.post.domain.exception.PostNotFoundException;
import nuri.nuri_server.domain.post.domain.repository.HashTagRepository;
import nuri.nuri_server.domain.post.domain.repository.PostFileRepository;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.post.presentation.dto.PostInfo;
import nuri.nuri_server.domain.post.presentation.dto.request.UpdatePostRequest;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatePostService {
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public void updatePost(UpdatePostRequest updatePostRequest, NuriUserDetails nuriUserDetails) {
        log.info("게시물 수정 요청: userId={}, postId={}",
                nuriUserDetails.getUser().getId(), updatePostRequest.postId());

        PostEntity post = postRepository.findById(updatePostRequest.postId())
                .orElseThrow(PostNotFoundException::new);

        post.validateAuthor(nuriUserDetails.getUser());

        PostInfo postInfo = updatePostRequest.postInfo();
        post.updatePost(postInfo.title(),
                postInfo.contents(),
                postInfo.shareRange(),
                postInfo.isGroup()
        );

        updatePostFile(updatePostRequest.files(), post);
        updatePostHashTag(updatePostRequest.hashTags(), post);

        log.info("게시물 수정 완료: postId={}, userId={}",
                post.getId(), nuriUserDetails.getUser().getId());
    }

    public void updatePostFile(List<String> files, PostEntity post) {
        postFileRepository.deleteAllByPostId(post.getId());

        List<PostFileEntity> postFileEntities = files.stream()
                .map(url -> PostFileEntity.builder()
                        .post(post)
                        .mediaUrl(url)
                        .build())
                .toList();

        postFileRepository.saveAll(postFileEntities);
    }

    public void updatePostHashTag(List<String> hashTags, PostEntity post) {
        hashTagRepository.deleteAllByPostId(post.getId());

        List<HashTagEntity> hashTagEntities = hashTags.stream()
                .map(tag -> HashTagEntity.builder()
                        .post(post)
                        .name(tag)
                        .build())
                .toList();

        hashTagRepository.saveAll(hashTagEntities);
    }
}
