package nuri.nuri_server.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.exception.DuplicateUserException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private void validateDuplicateUsername(String username) {
        if(userRepository.existsByUserId(username)) {
            throw new DuplicateUserException(username);
        }
    }
}
