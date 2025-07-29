package nuri.nuri_server.domain.post.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.repository.PostRepository;
import nuri.nuri_server.domain.post.domain.service.dto.PostInfo;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDomainService {
    private final PostRepository postRepository;

    public PostEntity savePost(UserEntity user, PostInfo postInfo) {
        PostEntity post = PostEntity.builder()
                .user(user)
                .title(postInfo.title())
                .contents(postInfo.contents())
                .shareRange(postInfo.shareRange())
                .isGroup(postInfo.isGroup())
                .build();

        return postRepository.save(post);
    }
}
