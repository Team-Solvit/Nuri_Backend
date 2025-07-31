package nuri.nuri_server.domain.boarding_house.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardingRoomInfo(
        @NotBlank(message = "방 이름(name)은 필수 항목입니다.")
        String name,

        String description,

        @NotNull(message = "월세(monthlyRent)는 필수 항목입니다.")
        Integer monthlyRent,

        @NotNull(message = "인원수(headCount)는 필수 항목입니다.")
        Integer headCount
) {}
