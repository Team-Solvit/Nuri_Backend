package nuri.nuri_server.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "page-size")
public class PageSizeProperties {
    private final Integer comment;
    private final Integer userPost;
    private final Integer snsPost;
    private final Integer boardingPost;
}
