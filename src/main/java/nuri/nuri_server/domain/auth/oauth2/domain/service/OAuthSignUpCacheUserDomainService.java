package nuri.nuri_server.domain.auth.oauth2.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.oauth2.application.service.exception.OAuthUserNotFoundException;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpCacheUser;
import nuri.nuri_server.domain.auth.oauth2.domain.repository.OAuthSignUpCacheUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthSignUpCacheUserDomainService {
    private final OAuthSignUpCacheUserRepository oauthSignUpCacheUserRepository;

    public OAuthSignUpCacheUser getOAuthSignUpTempUserById(String oauthId) {
        return oauthSignUpCacheUserRepository.findById(oauthId)
                .orElseThrow(() -> new OAuthUserNotFoundException(oauthId));
    }
}
