package nuri.nuri_server.domain.boarding_house.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.ContractPeriodEntity;

import java.util.UUID;

@Builder
public record ContractPeriod(
        UUID contractPeriodId,
        UUID roomId,
        Integer contractPeriod
) {
    public static ContractPeriod from(ContractPeriodEntity contractPeriod) {
        return ContractPeriod.builder()
                .contractPeriodId(contractPeriod.getId())
                .roomId(contractPeriod.getBoardingRoom().getId())
                .contractPeriod(contractPeriod.getContractPeriod())
                .build();
    }
}
