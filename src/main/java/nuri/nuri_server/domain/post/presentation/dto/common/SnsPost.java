package nuri.nuri_server.domain.post.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.share_range.ShareRange;
import nuri.nuri_server.domain.user.presentation.dto.common.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record SnsPost(
        UUID postId,
        String title,
        String contents,
        ShareRange shareRange,
        Boolean isGroup,
        List<SnsPostFileDto> files,
        Long likeCount,
        Long commentCount,
        LocalDate day,
        List<SnsHashtagDto> hashtags,
        UserDto author
) implements PostInfo {
    public static SnsPost from(PostEntity post, List<SnsPostFileDto> files, List<SnsHashtagDto> hashtags, Long likeCount, Long commentCount) {
        UserDto author = UserDto.from(post.getUser());
        return SnsPost.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .shareRange(post.getShareRange())
                .isGroup(post.getIsGroup())
                .files(files)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .day(post.getUpdatedAt().toLocalDate())
                .hashtags(hashtags)
                .author(author)
                .build();
    }
}
