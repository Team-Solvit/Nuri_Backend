package nuri.nuri_server.domain.post.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.service.HashTagDomainService;
import nuri.nuri_server.domain.post.domain.service.PostDomainService;
import nuri.nuri_server.domain.post.domain.service.PostFileDomainService;
import nuri.nuri_server.domain.post.presentation.dto.request.CreatePostRequest;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostDomainService postDomainService;
    private final PostFileDomainService postFileDomainService;
    private final HashTagDomainService hashTagDomainService;

    @Transactional
    public void createPost(CreatePostRequest createPostRequest, NuriUserDetails nuriUserDetails) {
        PostEntity post = postDomainService.savePost(nuriUserDetails.getUser(), createPostRequest.postInfo());
        postFileDomainService.savePostFiles(post, createPostRequest.files());
        hashTagDomainService.saveHashTags(post, createPostRequest.hashTags());
    }
}
