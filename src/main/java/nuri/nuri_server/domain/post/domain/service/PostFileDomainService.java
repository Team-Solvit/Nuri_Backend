package nuri.nuri_server.domain.post.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;
import nuri.nuri_server.domain.post.domain.repository.PostFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFileDomainService {
    private final PostFileRepository postFileRepository;

    public void savePostFiles(PostEntity post, List<String> files) {
        List<PostFileEntity> postFileEntities = files.stream()
                .map(url -> PostFileEntity.builder()
                        .post(post)
                        .mediaUrl(url)
                        .build())
                .toList();

        postFileRepository.saveAll(postFileEntities);
    }
}
