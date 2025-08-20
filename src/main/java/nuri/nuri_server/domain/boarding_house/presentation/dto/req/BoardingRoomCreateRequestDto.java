package nuri.nuri_server.domain.boarding_house.presentation.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BoardingRoomCreateRequestDto(
        @NotNull(message = "미디어(files)는 필수 항목입니다.")
        @Size(min = 1, message = "미디어는 최소 1개 이상 첨부해야 합니다.")
        List<@NotNull(message = "미디어 요소는 null일 수 없습니다.") String> files,

        @Valid
        BoardingRoomUpsertDto boardingRoomInfo,

        @NotNull(message = "계약기간(contractPeriod)은 필수 항목입니다.")
        @Size(min = 1, message = "계약기간은 최소 1개 이상 입력되어야 합니다.")
        List<@NotNull(message = "계약기간 요소는 null일 수 없습니다.") Integer> contractPeriod,

        @NotNull(message = "옵션(options)은 필수 항목입니다.")
        List<@NotNull(message = "옵션 요소는 null일 수 없습니다.") String> options
) {}
