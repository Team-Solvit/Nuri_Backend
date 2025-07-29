package nuri.nuri_server.global.security.aop;

import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.global.security.annotation.*;
import nuri.nuri_server.global.security.exception.GraphQLAccessDeniedException;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static nuri.nuri_server.domain.user.domain.role.Role.*;

@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(user)")
    public void checkUser(User user) {
        checkHasRole(USER, HOST, INTERNATIONAL_STUDENT, THIRD_PARTY, OPERATOR);
    }

    @Before("@annotation(host)")
    public void checkHost(Host host) {
        checkHasRole(HOST, OPERATOR);
    }

    @Before("@annotation(internationalStudent)")
    public void checkInternationalStudent(InternationalStudent internationalStudent) {
        checkHasRole(INTERNATIONAL_STUDENT, OPERATOR);
    }

    @Before("@annotation(thirdParty)")
    public void checkThirdParty(ThirdParty thirdParty) {
        checkHasRole(THIRD_PARTY, OPERATOR);
    }

    @Before("@annotation(boardingAuthUsers)")
    public void checkBoardingAuthUsers(BoardingAuthUsers boardingAuthUsers) {
        checkHasRole(HOST, INTERNATIONAL_STUDENT, THIRD_PARTY, OPERATOR);
    }

    @Before("@annotation(operator)")
    public void checkAdmin(Operator operator) {
        checkHasRole(OPERATOR);
    }

    private void checkHasRole(Role... roles) {
        List<String> strRoles = Arrays.stream(roles).map(Role::getValue).toList();
        
        Collection<? extends GrantedAuthority> authorities = getCurrentRole();
        if (authorities.stream().noneMatch(auth -> strRoles.contains(auth.getAuthority()))) {
            throw new GraphQLAccessDeniedException();
        }
    }

    private Collection<? extends GrantedAuthority> getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            throw new InvalidJsonWebTokenException();
        }
        return authentication.getAuthorities();
    }
}
