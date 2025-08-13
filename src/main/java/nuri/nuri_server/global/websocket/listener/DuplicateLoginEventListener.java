package nuri.nuri_server.global.websocket.listener;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.session.infra.event.DuplicateLoginEvent;
import nuri.nuri_server.global.exception.stomp.StompErrorResponse;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicateLoginEventListener {
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void onDuplicateLogin(DuplicateLoginEvent event) {
        StompErrorResponse stompErrorResponse = StompErrorResponse.builder()
                .message("중복 로그인이 감지되어 기존 세션은 종료됩니다. 다시 로그인해 주세요.")
                .build();

        messagingTemplate.convertAndSendToUser(
                event.getUserId(),
                "/exceptions",
                stompErrorResponse
        );
    }
}