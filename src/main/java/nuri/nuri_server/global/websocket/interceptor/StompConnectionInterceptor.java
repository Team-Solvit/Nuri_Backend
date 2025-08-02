package nuri.nuri_server.global.websocket.interceptor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.chat.domain.principal.ChatUserPrincipal;
import nuri.nuri_server.domain.session.domain.application.service.DuplicateLoginService;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompConnectionInterceptor implements ChannelInterceptor {
    private final DuplicateLoginService duplicateLoginService;
    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            String accessToken = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
            jwtProvider.checkAuthorization(accessToken);
            // 이거 JwtException 발생할 가능성이 있음
            String userId = jwtProvider.getUserIdFromToken(Objects.requireNonNull(accessToken).substring(7));

            String newSessionId = accessor.getSessionId();

            ChatUserPrincipal chatUserPrincipal = new ChatUserPrincipal(userId);

            accessor.setUser(chatUserPrincipal);

            log.info("STOMP 커넥션 USERID : {}", userId);

            log.info("SESSION : {}", newSessionId);

            duplicateLoginService.handleDuplicateLogin(userId, newSessionId);
        }

        return message;
    }
}
