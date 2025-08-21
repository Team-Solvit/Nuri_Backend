package nuri.nuri_server.domain.auth.oauth2.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.UserAgreementDto;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponseDto;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpCacheUser;
import nuri.nuri_server.domain.auth.oauth2.domain.service.OAuthSignUpCacheUserDomainService;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthSignUpRequestDto;
import nuri.nuri_server.domain.user.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.domain.exception.CountryNotFoundException;
import nuri.nuri_server.domain.user.domain.exception.DuplicateUserException;
import nuri.nuri_server.domain.user.domain.exception.LanguageNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.CountryRepository;
import nuri.nuri_server.domain.user.domain.entity.LanguageEntity;
import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.repository.LanguageRepository;
import nuri.nuri_server.domain.user.domain.repository.UserAgreementRepository;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2SignUpService {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final OAuthSignUpCacheUserDomainService oauthSignUpCacheUserDomainService;
    private final UserAgreementRepository userAgreementRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponseDto signUp(OAuthSignUpRequestDto oauthSignUpRequestDto) {
        OAuthSignUpCacheUser oauthSignUpCacheUser = oauthSignUpCacheUserDomainService.getOAuthSignUpTempUserById(oauthSignUpRequestDto.oauthId());

        UserEntity user = saveUserEntity(oauthSignUpRequestDto, oauthSignUpCacheUser);
        userAgree(user, oauthSignUpRequestDto.userAgreement());

        log.info("사용자 {}님이 로그인 하셨습니다.", user.getUserId());

        return jwtProvider.createTokenResponse(user);
    }

    private UserEntity saveUserEntity(OAuthSignUpRequestDto oauthSignUpRequestDto, OAuthSignUpCacheUser oauthSignUpCacheUser) {
        validateDuplicateUserId(oauthSignUpRequestDto.id());
        CountryEntity country = countryRepository.findByName(oauthSignUpRequestDto.country())
                .orElseThrow(() -> new CountryNotFoundException(oauthSignUpRequestDto.country()));

        LanguageEntity language = languageRepository.findByName(oauthSignUpRequestDto.language())
                .orElseThrow(() -> new LanguageNotFoundException(oauthSignUpRequestDto.language()));

        UserEntity userEntity = UserEntity.signupBuilder()
                .userId(oauthSignUpRequestDto.id())
                .country(country)
                .language(language)
                .name(oauthSignUpCacheUser.getName())
                .email(oauthSignUpRequestDto.email())
                .role(Role.USER)
                .profile(oauthSignUpCacheUser.getProfile())
                .oauthId(oauthSignUpCacheUser.getId())
                .oauthProvider(oauthSignUpCacheUser.getProvider())
                .build();

        return userRepository.save(userEntity);
    }

    public void validateDuplicateUserId(String userId) {
        if(userRepository.existsByUserId(userId)) throw new DuplicateUserException(userId);
    }

    private void userAgree(UserEntity user, UserAgreementDto userAgreement) {
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
