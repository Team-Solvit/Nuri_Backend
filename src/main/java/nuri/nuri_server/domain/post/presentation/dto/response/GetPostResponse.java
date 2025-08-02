package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;
import nuri.nuri_server.domain.post.presentation.dto.AuthorInfo;
import nuri.nuri_server.domain.post.presentation.dto.SnsPost;

@Builder
public record GetPostResponse(
        SnsPost post,
        AuthorInfo author
) {}
