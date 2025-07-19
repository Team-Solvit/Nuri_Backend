package nuri.nuri_server.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.security.filter.NuriAuthenticationFilter;
import nuri.nuri_server.global.security.filter.NuriExceptionFilter;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import nuri.nuri_server.global.security.user.NuriUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final NuriUserDetailsService nuriUserDetailsService;
    private final List<String> excludedUrls = List.of("/static/**", "/resources/**", "/public/**", "/auth/signup", "/auth/login", "/auth/reissue", "/graphql");

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(excludedUrls.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new NuriAuthenticationFilter(nuriUserDetailsService, jwtProvider, excludedUrls), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new NuriExceptionFilter(objectMapper), NuriAuthenticationFilter.class);

        return http.build();
    }
}
