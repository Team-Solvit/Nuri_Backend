package nuri.nuri_server.global.exception.stomp.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.stomp.StompErrorResponse;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompAuthenticationExceptionHandler extends StompSubProtocolErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]>clientMessage, Throwable ex) {
        Throwable cause = ex.getCause();
        try {
            if(cause instanceof JwtException) {
                StompErrorResponse stompErrorResponse = StompErrorResponse.builder()
                        .message("올바른 Jwt가 아닙니다.")
                        .build();
                return prepareErrorMessage(stompErrorResponse);
            }
            if(cause instanceof InvalidJsonWebTokenException) {
                StompErrorResponse stompErrorResponse = StompErrorResponse.builder()
                        .message("잘못된 형식의 Jwt 입니다.")
                        .build();
                return prepareErrorMessage(stompErrorResponse);
            }
        }
        catch (JsonProcessingException e) {
            return super.handleClientMessageProcessingError(clientMessage, ex);
        }
        loggingError((Exception) ex);
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> prepareErrorMessage(StompErrorResponse stompErrorResponse) throws JsonProcessingException {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);
        return MessageBuilder.createMessage(objectMapper.writeValueAsString(stompErrorResponse).getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

    private void loggingError(Exception exception) {
        log.error("예외 발생 : [{}] - 메세지 : [{}]", exception.getClass().getName(), exception.getMessage());
    }
}
