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
import nuri.nuri_server.domain.post.presentation.dto.common.PostUpsertDto;
import nuri.nuri_server.domain.post.presentation.dto.req.PostUpdateRequestDto;
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
    public void updatePost(PostUpdateRequestDto postUpdateRequestDto, NuriUserDetails nuriUserDetails) {
        log.info("게시물 수정 요청: userId={}, postId={}",
                nuriUserDetails.getUser().getId(), postUpdateRequestDto.postId());

        PostEntity post = postRepository.findById(postUpdateRequestDto.postId())
                .orElseThrow(PostNotFoundException::new);

        post.validateAuthor(nuriUserDetails.getUser());

        PostUpsertDto postInfo = postUpdateRequestDto.postInfo();
        post.updatePost(postInfo.title(),
                postInfo.contents(),
                postInfo.shareRange(),
                postInfo.isGroup()
        );

        updatePostFile(postUpdateRequestDto.files(), post);
        updatePostHashTag(postUpdateRequestDto.hashTags(), post);

        log.info("게시물 수정 완료: postId={}, userId={}",
                post.getId(), nuriUserDetails.getUser().getId());
    }

    private void updatePostFile(List<String> files, PostEntity post) {
        postFileRepository.deleteAllByPostId(post.getId());

        List<PostFileEntity> postFileEntities = files.stream()
                .map(url -> PostFileEntity.builder()
                        .post(post)
                        .mediaUrl(url)
                        .build())
                .toList();

        postFileRepository.saveAll(postFileEntities);
    }

    private void updatePostHashTag(List<String> hashTags, PostEntity post) {
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
