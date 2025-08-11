package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.PostEntity;
import nuri.nuri_server.domain.post.domain.share_range.ShareRange;
import nuri.nuri_server.domain.user.presentation.dto.UserInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record SnsPostInfo(
        UUID postId,
        String title,
        String contents,
        ShareRange shareRange,
        Boolean isGroup,
        List<SnsPostFile> files,
        Long likeCount,
        Long commentCount,
        LocalDate day,
        List<SnsHashtag> hashtags,
        UserInfo author
) implements PostListInfo {
    public static SnsPostInfo from(PostEntity post, List<SnsPostFile> files, List<SnsHashtag> hashtags, Long likeCount, Long commentCount) {
        UserInfo author = UserInfo.from(post.getUser());
        return SnsPostInfo.builder()
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
