package nuri.nuri_server.global.exception.stomp.handler;

import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.exception.stomp.StompErrorResponse;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class StompExceptionHandler {

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/exceptions")
    public StompErrorResponse handleException(Exception e) {
        loggingError(e);
        return StompErrorResponse.builder()
                .message("예상치 못한 오류가 발생했습니다.")
                .build();
    }

    private void loggingError(Exception exception) {
        log.error("예외 발생 : [{}] - 메세지 : [{}]", exception.getClass().getName(), exception.getMessage());
    }
}
