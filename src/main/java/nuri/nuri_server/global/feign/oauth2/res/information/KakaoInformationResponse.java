package nuri.nuri_server.global.feign.oauth2.res.information;

import lombok.Getter;

@Getter
public class KakaoInformationResponse {
    private long id;
    private KakaoAccount kakao_account;

    @Getter
    public static class KakaoAccount {
        private Profile profile;
        private String name;

        @Getter
        public static class Profile {
            private String profile_image_url;
        }
    }
}