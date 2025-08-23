package nuri.nuri_server.domain.auth.local.application.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.application.service.exception.PasswordMismatchException;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.LoginRequestDto;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.SignupRequestDto;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.UserAgreementDto;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponseDto;
import nuri.nuri_server.domain.user.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.domain.exception.CountryNotFoundException;
import nuri.nuri_server.domain.user.domain.exception.DuplicateUserException;
import nuri.nuri_server.domain.user.domain.exception.LanguageNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.CountryRepository;
import nuri.nuri_server.domain.user.domain.entity.LanguageEntity;
import nuri.nuri_server.domain.user.domain.repository.LanguageRepository;
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

    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;
    private final UserAgreementRepository userAgreementRepository;
    private final LanguageRepository languageRepository;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        validateDuplicateUserId(signupRequestDto.id());
        String userId = signupRequestDto.id();
        String password = passwordEncoder.encode(signupRequestDto.password());
        CountryEntity country = countryRepository.findByName(signupRequestDto.country())
                .orElseThrow(() -> new CountryNotFoundException(signupRequestDto.country()));

        LanguageEntity language = languageRepository.findByName(signupRequestDto.language())
                .orElseThrow(() -> new LanguageNotFoundException(signupRequestDto.language()));

        UserEntity userEntity = UserEntity.signupBuilder()
                .userId(userId)
                .country(country)
                .language(language)
                .name(signupRequestDto.name())
                .password(password)
                .email(signupRequestDto.email())
                .role(Role.USER)
                .build();

        userRepository.save(userEntity);

        userAgree(userEntity, signupRequestDto.userAgreement());
    }

    public void validateDuplicateUserId(String userId) {
        if(userRepository.existsByUserId(userId)) throw new DuplicateUserException(userId);
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        UserEntity userEntity = userRepository.findByUserId(loginRequestDto.id()).orElseThrow(() -> new UserNotFoundException(loginRequestDto.id()));
        if(!passwordEncoder.matches(loginRequestDto.password(), userEntity.getPassword())) {
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

        Role role = userRepository.findRoleByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return jwtProvider.createAccessToken(userId, role);
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