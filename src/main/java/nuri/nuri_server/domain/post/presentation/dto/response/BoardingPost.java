package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record BoardingPost(
        PostType type,
        String postId,
        List<String> files,
        String title,
        String contents,
        Long likes,
        Long comments,
        Integer price,
        LocalDate day
) implements PostListInfo {}
