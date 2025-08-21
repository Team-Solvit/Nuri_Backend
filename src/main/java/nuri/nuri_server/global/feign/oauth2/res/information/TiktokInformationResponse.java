package nuri.nuri_server.global.feign.oauth2.res.information;

import lombok.Getter;

@Getter
public class TiktokInformationResponse {
    private Data data;
    private Error error;

    @Getter
    public static class Data {
        private User user;
    }

    @Getter
    public static class User {
        private String open_id;
        private String avatar_url;
        private String display_name;
    }

    @Getter
    public static class Error {
        private String code;
        private String message;
        private String log_id;
    }
}
