package nuri.nuri_server.domain.notification.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.notification.domain.entity.NotificationEntity;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record NotificationDto(
        UUID notificationId,
        String content,
        Boolean checked,
        String link,
        LocalDate createAt
) {
    public static NotificationDto from(NotificationEntity notification) {
        return NotificationDto.builder()
                .notificationId(notification.getId())
                .content(notification.getContent())
                .checked(notification.getChecked())
                .link(notification.getLink())
                .createAt(notification.getCreatedAt().toLocalDate())
                .build();
    }
}