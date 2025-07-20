package nuri.nuri_server.domain.auth.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.UserAgreement;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthTempUser;
import nuri.nuri_server.domain.auth.oauth2.domain.repository.OAuthTempUserRepository;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthSignUpRequest;
import nuri.nuri_server.domain.auth.oauth2.service.exception.OAuthUserNotFoundException;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.country.domain.service.CountryService;
import nuri.nuri_server.domain.user.domain.entity.Language;
import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.LanguageNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.LanguageRepository;
import nuri.nuri_server.domain.user.domain.repository.UserAgreementRepository;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.domain.user.domain.service.UserDomainService;
import nuri.nuri_server.global.security.jwt.CookieManager;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nuri.nuri_server.domain.auth.local.application.service.AuthService.createTokenResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2SignUpService {
    private final UserRepository userRepository;
    private final CountryService countryService;
    private final UserDomainService userDomainService;
    private final LanguageRepository languageRepository;
    private final OAuthTempUserRepository oAuthTempUserRepository;
    private final UserAgreementRepository userAgreementRepository;
    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;

    @Transactional
    public TokenResponse signUp(OAuthSignUpRequest oAuthSignUpRequest) {
        OAuthTempUser oAuthTempUser = oAuthTempUserRepository.findById(oAuthSignUpRequest.id())
                .orElseThrow(() -> new OAuthUserNotFoundException(oAuthSignUpRequest.id()));

        UserEntity user = saveUserEntity(oAuthSignUpRequest, oAuthTempUser);
        userAgree(user, oAuthSignUpRequest.userAgreement());

        return createTokenResponse(user, jwtProvider, log, cookieManager);
    }

    private UserEntity saveUserEntity(OAuthSignUpRequest oAuthSignUpRequest, OAuthTempUser oAuthTempUser) {
        userDomainService.validateDuplicateUserId(oAuthSignUpRequest.id());
        CountryEntity country = countryService.getCountryEntity(oAuthSignUpRequest.country());
        Language language = languageRepository.findByName(oAuthSignUpRequest.language()).orElseThrow(() -> new LanguageNotFoundException(oAuthSignUpRequest.language()));

        UserEntity userEntity = UserEntity.signupBuilder()
                .userId(oAuthSignUpRequest.id())
                .country(country)
                .language(language)
                .name(oAuthTempUser.getName())
                .email(oAuthSignUpRequest.email())
                .role(Role.USER)
                .profile(oAuthTempUser.getProfile())
                .oauthId(oAuthTempUser.getId())
                .oauthProvider(oAuthTempUser.getProvider())
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
