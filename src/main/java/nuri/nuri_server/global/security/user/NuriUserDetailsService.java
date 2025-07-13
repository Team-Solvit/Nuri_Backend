package nuri.nuri_server.global.security.user;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.exception.IdNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NuriUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public NuriUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        return new NuriUserDetails(userEntity);
    }
}
