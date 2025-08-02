package nuri.nuri_server.domain.session.domain.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.session.domain.entity.SessionEntity;
import nuri.nuri_server.domain.session.domain.event.DuplicateLoginEvent;
import nuri.nuri_server.domain.session.domain.repository.SessionEntityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DuplicateLoginService {
    private final SessionEntityRepository sessionEntityRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${session.chat-expiration}")
    private Long chatExpiration;

    public void handleDuplicateLogin(String userId, String newSessionId) {
        SessionEntity oldSessionId = sessionEntityRepository.findById(userId).orElse(null);

        if (oldSessionId != null) {
            log.info("OLDSESSION : {}", oldSessionId.getSessionId());
        }
        log.info("NEWSESSION: {}", newSessionId);

        if(oldSessionId != null && !oldSessionId.getSessionId().equals(newSessionId)) {
            log.info("중복 로그인 이벤트 발생!!");
            eventPublisher.publishEvent(new DuplicateLoginEvent(this, userId));
        }

        sessionEntityRepository.save(
                SessionEntity.builder()
                        .userId(userId)
                        .sessionId(newSessionId)
                        .timeToLive(chatExpiration)
                        .build()
        );
    }
}
