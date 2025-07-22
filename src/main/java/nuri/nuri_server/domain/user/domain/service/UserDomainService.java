package nuri.nuri_server.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.DuplicateUserException;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.domain.user.domain.role.Role;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    public void validateDuplicateUserId(String userId) {
        if(userRepository.existsByUserId(userId)) {
            throw new DuplicateUserException(userId);
        }
    }

    public Role getRole(String userId) {
        return userRepository.findRoleByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntity getUser(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
