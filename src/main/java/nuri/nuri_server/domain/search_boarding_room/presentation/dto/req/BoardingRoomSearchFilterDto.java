package nuri.nuri_server.domain.search_boarding_room.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import nuri.nuri_server.domain.user.domain.gender.Gender;

public record BoardingRoomSearchFilterDto(
        String name,
        String region,
        IntRangeFilter contractPeriod,
        IntRangeFilter price,
        GeoFilter station,
        GeoFilter school,
        Gender gender,
        @NotNull(message = "하숙방 검색시 시작점(start)는 필수 입니다.")
        Integer start
) {}
