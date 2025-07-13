package nuri.nuri_server.domain.auth.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.application.service.exception.PasswordMismatchException;
import nuri.nuri_server.domain.auth.presentation.dto.req.LoginRequest;
import nuri.nuri_server.domain.auth.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.auth.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.country.application.service.CountryService;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.application.service.UserService;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.IdNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import nuri.nuri_server.global.security.jwt.RefreshTokenManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenManager refreshTokenManager;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        userService.validateDuplicateUserId(signupRequest.id());
        String userId = signupRequest.id();
        String password = passwordEncoder.encode(signupRequest.password());
        CountryEntity country = countryService.getCountryEntity(signupRequest.country());

        UserEntity userEntity = UserEntity.signupBuilder()
                .id(userId)
                .country(country)
                .name(signupRequest.name())
                .password(password)
                .email(signupRequest.email())
                .role(Role.USER)
                .build();

        userRepository.save(userEntity);
        userService.userAgree(userEntity, signupRequest);
    }

    @Transactional
    public TokenResponse login(@Valid LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findById(loginRequest.id()).orElseThrow(() -> new IdNotFoundException(loginRequest.id()));
        if(!passwordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            throw new PasswordMismatchException();
        }

        String accessToken = jwtProvider.createAccessToken(userEntity);
        String refreshToken = jwtProvider.createRefreshToken(loginRequest.id());

        String refreshTokenCookie = refreshTokenManager.createRefreshToken(userEntity.getId(), refreshToken);

        return new TokenResponse(accessToken, refreshTokenCookie);
    }

    public String logout(HttpServletRequest request) {
        String refreshToken = refreshTokenManager.getRefreshToken(request);

        return refreshTokenManager.deleteRefreshToken(refreshToken);
    }

    public TokenResponse reissue(HttpServletRequest request) {
        String refreshToken = refreshTokenManager.getRefreshToken(request);

        String userId = jwtProvider.getUserIdFromToken(refreshToken);
        String savedUserId = refreshTokenManager.getUserIdFromRefreshToken(refreshToken);

        if(!savedUserId.equals(userId)) {
            throw new InvalidJsonWebTokenException();
        }

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException(userId));

        String newAccessToken = jwtProvider.createAccessToken(userEntity);
        String refreshTokenCookie = refreshTokenManager.createRefreshCookie(refreshToken).toString();

        return new TokenResponse(newAccessToken, refreshTokenCookie);
    }
}
