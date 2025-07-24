package nuri.nuri_server.global.feign.oauth2.res.information;

import lombok.Getter;

@Getter
public class KakaoInformationResponse {
    private long id;
    private KakaoProperties properties;

    @Getter
    public static class KakaoProperties {
        private String profile_image;
        private String nickname;
    }
}