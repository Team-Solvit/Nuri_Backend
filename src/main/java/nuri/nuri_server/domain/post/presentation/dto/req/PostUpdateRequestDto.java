package nuri.nuri_server.domain.post.presentation.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nuri.nuri_server.domain.post.presentation.dto.common.PostUpsertDto;

import java.util.List;
import java.util.UUID;

public record PostUpdateRequestDto(
        @NotNull(message = "게시물 아이디(postId)는 필수 항목입니다.")
        UUID postId,

        @Valid
        PostUpsertDto postInfo,

        @NotNull(message = "미디어(files)는 필수 항목입니다.")
        @Size(min = 1, message = "미디어는 최소 1개 이상 첨부해야 합니다.")
        List<String> files,

        @NotNull(message = "해시테그(hashTags)는 필수 항목입니다.")
        List<String> hashTags
) {}
