package nuri.nuri_server.global.security.user;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.service.UserDomainService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NuriUserDetailsService implements UserDetailsService {

    private final UserDomainService userDomainService;

    @Override
    public NuriUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userDomainService.getUser(userId);
        return new NuriUserDetails(userEntity);
    }
}
