package nuri.nuri_server.domain.auth.oauth2.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.oauth2.application.service.exception.OAuthUserNotFoundException;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpTempUser;
import nuri.nuri_server.domain.auth.oauth2.domain.repository.OAuthSignUpTempUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthSignUpTempUserDomainService {
    private final OAuthSignUpTempUserRepository oauthSignUpTempUserRepository;

    public OAuthSignUpTempUser getOAuthSignUpTempUserById(String oauthId) {
        return oauthSignUpTempUserRepository.findById(oauthId)
                .orElseThrow(() -> new OAuthUserNotFoundException(oauthId));
    }
}
