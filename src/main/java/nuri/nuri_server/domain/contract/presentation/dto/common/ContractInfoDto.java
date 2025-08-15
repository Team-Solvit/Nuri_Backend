package nuri.nuri_server.domain.contract.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.contract_status.ContractStatus;
import nuri.nuri_server.domain.boarding_house.domain.entity.ContractEntity;
import nuri.nuri_server.domain.user.presentation.dto.BoarderDto;

import java.time.LocalDate;

@Builder
public record ContractInfoDto(
        BoarderDto boarder,
        LocalDate expiryDate,
        ContractStatus status
) {
    public static ContractInfoDto from(ContractEntity contract) {
        BoarderDto boarder = BoarderDto.from(contract.getBoarder());
        return ContractInfoDto.builder()
                .boarder(boarder)
                .expiryDate(contract.getExpiryDate())
                .status(contract.getStatus())
                .build();
    }
}
