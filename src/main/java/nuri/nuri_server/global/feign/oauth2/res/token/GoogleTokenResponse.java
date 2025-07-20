package nuri.nuri_server.global.feign.oauth2.res.token;

public record GoogleTokenResponse(
    String access_token,
    Long expires_in,
    String token_type,
    String scope,
    String refresh_token
) {}
