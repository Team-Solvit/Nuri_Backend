package nuri.nuri_server.global.security.user;

import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public record NuriUserDetails(UserEntity user) implements UserDetails, Principal {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    public UUID getId() {
        return user.getId();
    }

    public String getProfile() {
        return user.getProfile();
    }

    public String getNickname() {
        return user.getName();
    }

    @Override
    public String getName() {
        return user.getUserId();
    }
}
