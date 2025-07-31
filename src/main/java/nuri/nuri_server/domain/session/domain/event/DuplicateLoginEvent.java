package nuri.nuri_server.domain.session.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DuplicateLoginEvent extends ApplicationEvent {
    private final String userId;
    public DuplicateLoginEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }
}
