package nuri.nuri_server.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.exception.ErrorResponse;
import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class NuriExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (JwtException | InvalidJsonWebTokenException e) {
            handleJwtException(response, e.getMessage());
        }
    }

    private void handleJwtException(HttpServletResponse response, String message) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        GraphQLError graphQLError = GraphqlErrorBuilder.newError()
                .message(message)
                .errorType(ErrorType.UNAUTHENTICATED)
                .extensions(errorResponse.getMap())
                .build();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", null);
        responseBody.put("errors", Collections.singletonList(graphQLError));

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
