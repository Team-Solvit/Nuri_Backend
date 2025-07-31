package nuri.nuri_server.domain.post.presentation.dto;

import jakarta.validation.constraints.NotNull;
import nuri.nuri_server.domain.post.domain.share_range.ShareRange;

public record PostInfo(
        String title,

        String contents,

        @NotNull(message = "공개범위(shareRange)는 필수 항목입니다.")
        ShareRange shareRange,

        Boolean isGroup
) {}
