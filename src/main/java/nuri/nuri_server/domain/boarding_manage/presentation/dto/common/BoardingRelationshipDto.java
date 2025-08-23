package nuri.nuri_server.domain.boarding_manage.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingRelationshipEntity;
import nuri.nuri_server.domain.user.presentation.dto.BoarderDto;
import nuri.nuri_server.domain.user.presentation.dto.ThirdPartyDto;

import java.util.UUID;

@Builder
public record BoardingRelationshipDto(
        UUID relationshipId,
        ThirdPartyDto thirdParty,
        BoarderDto boarder,
        BoardingHouseDto boarderHouse,
        BoardingRoomDto boardingRoom
) {
    public static BoardingRelationshipDto from(BoardingRelationshipEntity relationship, BoardingRoomDto boardingRoom) {
        ThirdPartyDto thirdParty = ThirdPartyDto.from(relationship.getThirdParty());
        BoarderDto boarder = BoarderDto.from(relationship.getBoarder());
        BoardingHouseDto boardingHouse = BoardingHouseDto.from(relationship.getBoardingHouse());
        return BoardingRelationshipDto.builder()
                .relationshipId(relationship.getId())
                .thirdParty(thirdParty)
                .boarder(boarder)
                .boarderHouse(boardingHouse)
                .boardingRoom(boardingRoom)
                .build();
    }
}
