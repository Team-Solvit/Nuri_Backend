package nuri.nuri_server.domain.auth.local.application.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.application.service.exception.PasswordMismatchException;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.LoginRequest;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.UserAgreement;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.country.domain.service.CountryService;
import nuri.nuri_server.domain.user.domain.entity.Language;
import nuri.nuri_server.domain.user.domain.service.LanguageDomainService;
import nuri.nuri_server.domain.user.domain.service.UserDomainService;
import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserAgreementRepository;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.global.security.jwt.CookieManager;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserDomainService userDomainService;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;
    private final UserAgreementRepository userAgreementRepository;
    private final LanguageDomainService languageDomainService;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        userDomainService.validateDuplicateUserId(signupRequest.id());
        String userId = signupRequest.id();
        String password = passwordEncoder.encode(signupRequest.password());
        CountryEntity country = countryService.getCountryEntity(signupRequest.country());

        Language language = languageDomainService.getLanguageByName(signupRequest.language());

        UserEntity userEntity = UserEntity.signupBuilder()
                .userId(userId)
                .country(country)
                .language(language)
                .name(signupRequest.name())
                .password(password)
                .email(signupRequest.email())
                .role(Role.USER)
                .build();

        userRepository.save(userEntity);

        userAgree(userEntity, signupRequest.userAgreement());
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByUserId(loginRequest.id()).orElseThrow(() -> new UserNotFoundException(loginRequest.id()));
        if(!passwordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            throw new PasswordMismatchException();
        }

        log.info("사용자 {}님이 로그인 하셨습니다.", userEntity.getUserId());

        return jwtProvider.createTokenResponse(userEntity);
    }

    public String logout(NuriUserDetails nuriUserDetails) {
        return cookieManager.deleteRefreshToken(nuriUserDetails.getUsername());
    }

    public String reissue(HttpServletRequest request) {
        String refreshToken = cookieManager.getRefreshToken(request);
        String userId = jwtProvider.getUserIdFromToken(refreshToken);

        cookieManager.validateRefreshToken(userId, refreshToken);

        Role role = userDomainService.getRole(userId);

        return jwtProvider.createAccessToken(userId, role);
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
