package nuri.nuri_server.global.feign.oauth2.res.information;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoProperties(
    @JsonProperty("profile_image")
    String profileImage,
    String nickname
) {}
