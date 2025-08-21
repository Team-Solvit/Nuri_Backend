package nuri.nuri_server.global.feign.oauth2.res.information;

import lombok.Getter;

@Getter
public class FacebookInformationResponse {
    private String id;
    private String name;
    private Picture picture;

    @Getter
    public static class Picture {
        private PictureData data;

        @Getter
        public static class PictureData {
            private int height;
            private boolean is_silhouette;
            private String url;
            private int width;
        }
    }
}
