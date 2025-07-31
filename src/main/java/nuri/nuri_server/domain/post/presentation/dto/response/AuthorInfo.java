package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;

@Builder
public record AuthorInfo(
        String authorId,
        String profile,
        String name
) {}
