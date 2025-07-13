package nuri.nuri_server.domain.auth.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.presentation.dto.req.LoginRequest;
import nuri.nuri_server.domain.auth.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.auth.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.country.application.service.CountryService;
import nuri.nuri_server.domain.country.domain.entity.CountryEntity;
import nuri.nuri_server.domain.user.application.service.UserService;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
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
}
