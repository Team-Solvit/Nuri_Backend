package nuri.nuri_server.global.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.security.filter.request.CachedBodyHttpServletRequest;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import nuri.nuri_server.global.security.user.NuriUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class NuriAuthenticationFilter extends OncePerRequestFilter {

    private final NuriUserDetailsService nuriUserDetailsService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final List<String> excludedResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedRequest = (CachedBodyHttpServletRequest) request;
        String body = cachedRequest.getCachedBodyAsString();
        JsonNode jsonNode = objectMapper.readTree(body);

        String operationName = jsonNode.has("operationName") && !jsonNode.get("operationName").isNull()
                ? jsonNode.get("operationName").asText()
                : "";

        System.out.println("body = " + body);
        System.out.println("operationName = " + operationName);
        System.out.println("excludedResolver = " + excludedResolver);

        if (excludedResolver.contains(operationName)) {
            filterChain.doFilter(cachedRequest, response);
            return;
        }

        String accessToken = jwtProvider.getAccessToken(request);
        String userId = jwtProvider.getUserIdFromToken(accessToken);

        NuriUserDetails nuriUserDetails = nuriUserDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nuriUserDetails, null, nuriUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
