package nuri.nuri_server.domain.notification.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

import java.util.UUID;

public class NotificationNotFoundException extends EntityNotFoundException {
    public NotificationNotFoundException(UUID notificationId) {
        super("알림 아이디가 " + notificationId + " 인 알림을 찾을 수 없습니다.");
    }
}
