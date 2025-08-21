package nuri.nuri_server.domain.post.presentation.dto.req;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserPostListReadRequestDto(
        @NotNull(message = "유저 게시물 리스트 조회시 유저 아이디(userId)는 필수 항목입니다.")
        UUID userId,

        @NotNull(message = "유저 게시물 리스트 조회시 시작위치(start)는 필수 항목입니다.")
        Integer start
) {}
