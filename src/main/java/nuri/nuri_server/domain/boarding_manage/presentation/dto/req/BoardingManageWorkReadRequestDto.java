package nuri.nuri_server.domain.boarding_manage.presentation.dto.req;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record BoardingManageWorkReadRequestDto(
        @NotNull(message = "제3자 하숙관리 업무 조회시 날짜(day)는 필수 항목입니다.")
        LocalDate date,

        UUID houseId
) {}
