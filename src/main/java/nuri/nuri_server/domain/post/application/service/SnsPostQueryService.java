package nuri.nuri_server.domain.post.application.service;


import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.repository.PostCommentRepository;
import nuri.nuri_server.domain.post.domain.repository.HashTagRepository;
import nuri.nuri_server.domain.post.domain.repository.PostFileRepository;
import nuri.nuri_server.domain.post.domain.repository.PostLikeRepository;
import nuri.nuri_server.domain.post.presentation.dto.common.SnsHashtagDto;
import nuri.nuri_server.domain.post.presentation.dto.common.SnsPost;
import nuri.nuri_server.domain.post.presentation.dto.common.SnsPostFileDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SnsPostQueryService {
    private final PostFileRepository postFileRepository;
    private final HashTagRepository hashTagRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    public SnsPost getSnsPost(PostEntity post) {
        Long likeCount = postLikeRepository.countByPostId(post.getId());
        Long commentCount = postCommentRepository.countByPostId(post.getId());

        List<SnsPostFileDto> files = postFileRepository.findAllByPostId(post.getId()).stream()
                .map(SnsPostFileDto::from)
                .toList();

        List<SnsHashtagDto> hashtags = hashTagRepository.findAllByPostId(post.getId()).stream()
                .map(SnsHashtagDto::from)
                .toList();

        return SnsPost.from(post,files, hashtags, likeCount, commentCount);
    }
}
