package nuri.nuri_server.domain.post.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.repository.HashTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashTagDomainService {
    private final HashTagRepository hashTagRepository;

    public void saveHashTags(PostEntity post, List<String> hashTags) {
        List<HashTagEntity> hashTagEntities = hashTags.stream()
                .map(tag -> HashTagEntity.builder()
                        .post(post)
                        .name(tag)
                        .build())
                .toList();

        hashTagRepository.saveAll(hashTagEntities);
    }
}
