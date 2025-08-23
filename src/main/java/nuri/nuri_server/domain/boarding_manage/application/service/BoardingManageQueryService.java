package nuri.nuri_server.domain.boarding_manage.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomQueryService;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingManageWorkEntity;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingRelationshipEntity;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.common.BoardingManageWorkDto;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.common.BoardingRelationshipDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardingManageQueryService {

    private final BoardingRoomQueryService boardingRoomQueryService;

    public BoardingRelationshipDto getBoardingRelationshipDto(BoardingRelationshipEntity relationship) {
        BoardingRoomDto room = boardingRoomQueryService.getBoardingRoomDto(relationship.getBoardingRoom());
        return BoardingRelationshipDto.from(relationship, room);
    }

    public BoardingManageWorkDto getBoardingManageWorkDto(BoardingManageWorkEntity work) {
        BoardingRelationshipDto relationship = getBoardingRelationshipDto(work.getBoardingRelationship());
        return BoardingManageWorkDto.from(work, relationship);
    }
}
