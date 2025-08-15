package nuri.nuri_server.domain.boarding_manage.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BoardingManageWorkFileUploadRequestDto(
        @NotNull(message = "하숙관리 기록 업로드시 업무 아이디(workId)는 필수 항목입니다.")
        UUID workId,

        @NotBlank(message = "하숙관리 기록 업로드시 기록 파일(file)은 필수 항목입니다.")
        String file
) {}
