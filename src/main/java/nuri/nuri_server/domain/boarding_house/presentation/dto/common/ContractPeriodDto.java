package nuri.nuri_server.domain.boarding_house.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.ContractPeriodEntity;

import java.util.UUID;

@Builder
public record ContractPeriodDto(
        UUID contractPeriodId,
        UUID roomId,
        Integer contractPeriod
) {
    public static ContractPeriodDto from(ContractPeriodEntity contractPeriod) {
        return ContractPeriodDto.builder()
                .contractPeriodId(contractPeriod.getId())
                .roomId(contractPeriod.getBoardingRoom().getId())
                .contractPeriod(contractPeriod.getContractPeriod())
                .build();
    }
}
