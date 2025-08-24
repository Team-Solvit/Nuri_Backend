package nuri.nuri_server.domain.notification.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.notification.application.service.NotificationService;
import nuri.nuri_server.domain.notification.presentation.dto.common.NotificationDto;
import nuri.nuri_server.global.security.annotation.User;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @User
    @QueryMapping
    public Long getNotificationCount(
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return notificationService.countNotification(nuriUserDetails);
    }

    @User
    @QueryMapping
    public List<NotificationDto> getNotificationList(
            @Argument("start") @NotNull(message = "알림 리스트 조회시 시작점(start)는 필수항목입니다.") Integer start,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return notificationService.getNotificationList(nuriUserDetails, start);
    }

    @User
    @MutationMapping
    public String checkNotification(
            @Argument("notificationId") @NotNull(message = "알림 일음 시 알림 아이디(notificationId)는 필수항목입니다.") UUID notificationId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        notificationService.checkNotification(nuriUserDetails, notificationId);
        return "알림을 읽음으로 처리 완료하였습니다.";
    }
}
