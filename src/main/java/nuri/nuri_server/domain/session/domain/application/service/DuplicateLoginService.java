package nuri.nuri_server.domain.session.domain.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.session.domain.entity.SessionEntity;
import nuri.nuri_server.domain.session.domain.event.DuplicateLoginEvent;
import nuri.nuri_server.domain.session.domain.repository.SessionEntityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicateLoginService {
    private final SessionEntityRepository sessionEntityRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${session.chat-expiration}")
    private Long chatExpiration;

    public void handleDuplicateLogin(String userId, String newSessionId) {
        SessionEntity oldSessionId = sessionEntityRepository.findById(userId).orElse(null);

        if(oldSessionId != null && !oldSessionId.getSessionId().equals(newSessionId)) {
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
