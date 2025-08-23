package nuri.nuri_server.domain.boarding_manage.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingManageWorkEntity;
import nuri.nuri_server.domain.boarding_manage.domain.manage_type.BoardingManageType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BoardingManageWorkDto(
        UUID manageWorkId,
        BoardingRelationshipDto relationship,
        String name,
        LocalDate date,
        String file,
        BoardingManageType type,
        Boolean status
) {
    public static BoardingManageWorkDto from(BoardingManageWorkEntity work, BoardingRelationshipDto relationship) {
        return BoardingManageWorkDto.builder()
                .manageWorkId(work.getId())
                .relationship(relationship)
                .name(work.getName())
                .date(work.getDate())
                .file(work.getFile())
                .type(work.getType())
                .status(work.getStatus())
                .build();
    }
}
