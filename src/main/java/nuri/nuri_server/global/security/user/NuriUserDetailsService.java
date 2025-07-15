package nuri.nuri_server.global.security.user;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NuriUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public NuriUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return new NuriUserDetails(userEntity);
    }
}
