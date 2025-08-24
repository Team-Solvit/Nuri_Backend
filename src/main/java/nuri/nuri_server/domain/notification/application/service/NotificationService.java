package nuri.nuri_server.domain.notification.application.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.notification.domain.entity.NotificationEntity;
import nuri.nuri_server.domain.notification.domain.exception.NotificationNotFoundException;
import nuri.nuri_server.domain.notification.domain.repository.NotificationRepository;
import nuri.nuri_server.domain.notification.presentation.dto.common.NotificationDto;
import nuri.nuri_server.global.properties.PageSizeProperties;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PageSizeProperties pageSizeProperties;

    @Transactional(readOnly = true)
    public Long countNotification(NuriUserDetails nuriUserDetails) {
        log.info("알림 갯수 요청: userId={}", nuriUserDetails.getId());

        Long notificationCount = notificationRepository.countAllByUserId(nuriUserDetails.getId());

        log.info("알림 갯수 반환: notificationCount={}", notificationCount);
        return notificationCount;
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationList(NuriUserDetails nuriUserDetails, Integer start) {
        log.info("알림 리스트 요청: userId={}", nuriUserDetails.getId());

        Integer size = pageSizeProperties.getNotification();
        Pageable pageable = PageRequest.of(start, size, Sort.by("updatedAt").descending());
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByUserId(nuriUserDetails.getUser().getId(), pageable);

        List<NotificationDto> notifications = notificationEntities.stream()
                        .map(NotificationDto::from)
                        .toList();

        log.info("알림 리스트 반환: notificationCount={}", notifications.size());
        return notifications;
    }

    @Transactional
    public void checkNotification(NuriUserDetails nuriUserDetails, @NotNull(message = "알림 일음 시 알림 아이디(notificationId)는 필수항목입니다.") UUID notificationId) {
        log.info("알림 읽음 요청: userId={}", nuriUserDetails.getId());

        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        notification.validateUser(nuriUserDetails.getUser());

        notification.check();
        log.info("알림 읽음 완료: notificationId={}", notification.getId());
    }
}
