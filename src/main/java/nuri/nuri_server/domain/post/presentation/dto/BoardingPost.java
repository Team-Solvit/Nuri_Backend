package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record BoardingPost(
        PostType type,
        UUID postId,
        List<String> files,
        String title,
        String contents,
        Long likes,
        Long comments,
        Integer price,
        LocalDate day
) implements PostListInfo {}
