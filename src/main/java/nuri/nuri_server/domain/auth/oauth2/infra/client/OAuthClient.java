package nuri.nuri_server.domain.auth.oauth2.infra.client;


import nuri.nuri_server.domain.auth.oauth2.infra.client.dto.OAuth2InformationResponse;

public interface OAuthClient {
    String getAccessToken(String code);
    OAuth2InformationResponse getUserInfo(String accessToken);
}
