package nuri.nuri_server.global.websocket.interceptor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.session.domain.entity.SessionEntity;
import nuri.nuri_server.domain.session.infra.event.DuplicateLoginEvent;
import nuri.nuri_server.domain.session.domain.repository.SessionEntityRepository;
import nuri.nuri_server.global.properties.SessionProperties;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import nuri.nuri_server.global.security.user.NuriUserDetailsService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StompConnectionInterceptor implements ChannelInterceptor {
    private final JwtProvider jwtProvider;
    private final SessionEntityRepository sessionEntityRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final NuriUserDetailsService nuriUserDetailsService;
    private final SessionProperties sessionProperties;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor
                .getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String accessToken = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
            jwtProvider.checkAuthorization(accessToken);
            String userId = jwtProvider.getUserIdFromToken(Objects.requireNonNull(accessToken).substring(7));
            String newSessionId = accessor.getSessionId();
            NuriUserDetails nuriUserDetails = nuriUserDetailsService.loadUserByUsername(userId);
            accessor.setUser(nuriUserDetails);
            handleDuplicateLogin(userId, newSessionId);
            return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
        }

        return message;
    }

    private void handleDuplicateLogin(String userId, String newSessionId) {
        SessionEntity oldSessionId = sessionEntityRepository.findById(userId).orElse(null);

        if(oldSessionId != null && !oldSessionId.getSessionId().equals(newSessionId)) {
            eventPublisher.publishEvent(new DuplicateLoginEvent(this, userId));
        }

        sessionEntityRepository.save(
                SessionEntity.builder()
                        .userId(userId)
                        .sessionId(newSessionId)
                        .timeToLive(sessionProperties.getChatExpiration())
                        .build()
        );
    }
}