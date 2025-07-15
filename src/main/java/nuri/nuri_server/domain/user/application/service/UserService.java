package nuri.nuri_server.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.DuplicateUserException;
import nuri.nuri_server.domain.user.domain.repository.UserAgreementRepository;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAgreementRepository userAgreementRepository;

    // exsitsById로 하셈 쿼리 성능 개 후짐
    public void validateDuplicateUserId(String userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    throw new DuplicateUserException(userId);
                });
    }
}
