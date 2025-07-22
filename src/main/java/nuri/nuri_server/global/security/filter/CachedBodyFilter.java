package nuri.nuri_server.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import nuri.nuri_server.global.security.filter.request.CachedBodyHttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CachedBodyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
        filterChain.doFilter(cachedRequest, response);
    }
}