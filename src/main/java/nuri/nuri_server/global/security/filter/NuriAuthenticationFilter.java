package nuri.nuri_server.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import nuri.nuri_server.global.security.user.NuriUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class NuriAuthenticationFilter extends OncePerRequestFilter {

    private final NuriUserDetailsService nuriUserDetailsService;
    private final JwtProvider jwtProvider;
    private final List<String> excludedUrls;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getAccessToken(request);
        String userId = jwtProvider.getUserIdFromToken(accessToken);

        NuriUserDetails nuriUserDetails = nuriUserDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nuriUserDetails, null, nuriUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return excludedUrls.stream()
                .anyMatch(pattern ->
                        new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}
