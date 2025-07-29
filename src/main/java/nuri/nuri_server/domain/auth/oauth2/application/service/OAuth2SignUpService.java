package nuri.nuri_server.domain.auth.oauth2.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.UserAgreement;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpCacheUser;
import nuri.nuri_server.domain.auth.oauth2.domain.service.OAuthSignUpCacheUserDomainService;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthSignUpRequest;
import nuri.nuri_server.domain.user.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.domain.service.CountryService;
import nuri.nuri_server.domain.user.domain.entity.LanguageEntity;
import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.repository.UserAgreementRepository;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.domain.user.domain.service.LanguageDomainService;
import nuri.nuri_server.domain.user.domain.service.UserDomainService;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2SignUpService {
    private final UserRepository userRepository;
    private final CountryService countryService;
    private final UserDomainService userDomainService;
    private final LanguageDomainService languageDomainService;
    private final OAuthSignUpCacheUserDomainService oauthSignUpCacheUserDomainService;
    private final UserAgreementRepository userAgreementRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse signUp(OAuthSignUpRequest oauthSignUpRequest) {
        OAuthSignUpCacheUser oauthSignUpCacheUser = oauthSignUpCacheUserDomainService.getOAuthSignUpTempUserById(oauthSignUpRequest.oauthId());

        UserEntity user = saveUserEntity(oauthSignUpRequest, oauthSignUpCacheUser);
        userAgree(user, oauthSignUpRequest.userAgreement());

        log.info("사용자 {}님이 로그인 하셨습니다.", user.getUserId());

        return jwtProvider.createTokenResponse(user);
    }

    private UserEntity saveUserEntity(OAuthSignUpRequest oauthSignUpRequest, OAuthSignUpCacheUser oauthSignUpCacheUser) {
        userDomainService.validateDuplicateUserId(oauthSignUpRequest.id());
        CountryEntity country = countryService.getCountryEntity(oauthSignUpRequest.country());
        LanguageEntity language = languageDomainService.getLanguageByName(oauthSignUpRequest.language());

        UserEntity userEntity = UserEntity.signupBuilder()
                .userId(oauthSignUpRequest.id())
                .country(country)
                .language(language)
                .name(oauthSignUpCacheUser.getName())
                .email(oauthSignUpRequest.email())
                .role(Role.USER)
                .profile(oauthSignUpCacheUser.getProfile())
                .oauthId(oauthSignUpCacheUser.getId())
                .oauthProvider(oauthSignUpCacheUser.getProvider())
                .build();

        return userRepository.save(userEntity);
    }

    private void userAgree(UserEntity user, UserAgreement userAgreement) {
        UserAgreementEntity userAgreementEntity = UserAgreementEntity.userAgreeBuilder()
                .user(user)
                .agreedTermsOfService(userAgreement.agreedTermsOfService())
                .agreedPrivacyCollection(userAgreement.agreedPrivacyCollection())
                .agreedPrivacyThirdParty(userAgreement.agreedPrivacyThirdParty())
                .agreedIdentityAgencyTerms(userAgreement.agreedIdentityAgencyTerms())
                .agreedIdentityPrivacyDelegate(userAgreement.agreedIdentityPrivacyDelegate())
                .agreedIdentityUniqueInfo(userAgreement.agreedIdentityUniqueInfo())
                .agreedIdentityProviderTerms(userAgreement.agreedIdentityProviderTerms())
                .build();

        userAgreementRepository.save(userAgreementEntity);
    }
}
