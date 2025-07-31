package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;
import nuri.nuri_server.domain.post.domain.repository.HashTagRepository;
import nuri.nuri_server.domain.post.domain.repository.PostFileRepository;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.post.presentation.dto.request.CreatePostRequest;
import nuri.nuri_server.domain.post.presentation.dto.PostInfo;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatePostService {
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public void createPost(CreatePostRequest createPostRequest, NuriUserDetails nuriUserDetails) {
        log.info("게시물 생성 요청: userId={}, title={}",
                nuriUserDetails.getUser().getId(),
                createPostRequest.postInfo().title());
        PostEntity post = savePost(nuriUserDetails.getUser(), createPostRequest.postInfo());
        log.info("게시물 저장 완료: postId={}", post.getId());
        savePostFiles(post, createPostRequest.files());
        saveHashTags(post, createPostRequest.hashTags());
    }

    private PostEntity savePost(UserEntity user, PostInfo postInfo) {
        PostEntity post = PostEntity.builder()
                .user(user)
                .title(postInfo.title())
                .contents(postInfo.contents())
                .shareRange(postInfo.shareRange())
                .isGroup(postInfo.isGroup())
                .build();

        return postRepository.save(post);
    }

    private void savePostFiles(PostEntity post, List<String> files) {
        List<PostFileEntity> postFileEntities = files.stream()
                .map(url -> PostFileEntity.builder()
                        .post(post)
                        .mediaUrl(url)
                        .build())
                .toList();

        postFileRepository.saveAll(postFileEntities);
        log.info("파일 {}개 저장 완료 (postId={})", postFileEntities.size(), post.getId());
    }

    private void saveHashTags(PostEntity post, List<String> hashTags) {
        List<HashTagEntity> hashTagEntities = hashTags.stream()
                .map(tag -> HashTagEntity.builder()
                        .post(post)
                        .name(tag)
                        .build())
                .toList();

        hashTagRepository.saveAll(hashTagEntities);
        log.info("해시태그 {}개 저장 완료 (postId={})", hashTagEntities.size(), post.getId());
    }
}