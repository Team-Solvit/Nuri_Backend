package nuri.nuri_server.global.feign.oauth2.res.token;

public record FacebookTokenResponse(
        String access_token,
        String token_type,
        Long expires_in
) {}
