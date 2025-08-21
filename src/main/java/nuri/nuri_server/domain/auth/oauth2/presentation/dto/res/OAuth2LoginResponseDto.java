package nuri.nuri_server.domain.auth.oauth2.presentation.dto.res;

public record OAuth2LoginResponseDto(
        String oauthId,
        Boolean isNewUser
) {
    public static OAuth2LoginResponseDto from(OAuth2LoginValue value) {
        return new OAuth2LoginResponseDto(value.getOauthId(), value.isNewUser());
    }
}
