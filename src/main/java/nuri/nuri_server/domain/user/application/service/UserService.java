package nuri.nuri_server.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.user.domain.entity.UserAgreementEntity;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.DuplicateIdException;
import nuri.nuri_server.domain.user.domain.exception.IdNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserAgreementRepository;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAgreementRepository userAgreementRepository;

    public void validateDuplicateUserId(String userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    throw new DuplicateIdException(userId);
                });
    }

    public void userAgree(UserEntity user, SignupRequest signupRequest) {
        UserAgreementEntity userAgreementEntity = UserAgreementEntity.userAgreeBuilder()
                .user(user)
                .agreedTermsOfService(signupRequest.agreedTermsOfService())
                .agreedPrivacyCollection(signupRequest.agreedPrivacyCollection())
                .agreedPrivacyThirdParty(signupRequest.agreedPrivacyThirdParty())
                .agreedIdentityAgencyTerms(signupRequest.agreedIdentityProviderTerms())
                .agreedIdentityPrivacyDelegate(signupRequest.agreedIdentityPrivacyDelegate())
                .agreedIdentityUniqueInfo(signupRequest.agreedIdentityUniqueInfo())
                .agreedIdentityProviderTerms(signupRequest.agreedIdentityProviderTerms())
                .build();

        userAgreementRepository.save(userAgreementEntity);
    }
}
