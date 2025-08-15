package nuri.nuri_server.domain.boarding_manage.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.contract.application.service.ContractQueryService;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingRelationshipEntity;
import nuri.nuri_server.domain.boarding_manage.domain.repository.BoardingRelationshipRepository;
import nuri.nuri_server.domain.user.domain.exception.ThirdPartyNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.ThirdPartyRepository;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetBoardingManageService {

    private final BoardingRelationshipRepository boardingRelationshipRepository;
    private final ThirdPartyRepository thirdPartyRepository;
    private final ContractQueryService contractQueryService;

    @Transactional(readOnly = true)
    public List<BoardingHouseDto> getManageBoardingHouseList(NuriUserDetails nuriUserDetails) {
        UUID thirdPartyId = nuriUserDetails.getId();
        log.info("관리 하숙집 리스트 요청: thirdPartyId={}", thirdPartyId);

        validateThirdPartyId(thirdPartyId);

        List<BoardingRelationshipEntity> relationships = boardingRelationshipRepository.findAllByThirdPartyId(thirdPartyId);

        log.info("관리 하숙집 리스트 반환: relationshipCount={}", relationships.size());
        return relationships.stream()
                .map(relationship -> BoardingHouseDto.from(relationship.getBoarderHouse()))
                .toList();
    }

    private void validateThirdPartyId(UUID thirdPartyId) {
        if(!thirdPartyRepository.existsById(thirdPartyId)) {
            throw new ThirdPartyNotFoundException(thirdPartyId);
        }
    }

    @Transactional(readOnly = true)
    public List<RoomContractDto> getManageBoardingRoomList(NuriUserDetails nuriUserDetails, UUID houseId) {
        UUID thirdPartyId = nuriUserDetails.getId();
        log.info("관리 하숙방 리스트 요청: thirdPartyId={}", thirdPartyId);

        validateThirdPartyId(thirdPartyId);

        List<BoardingRoomEntity> boardingRooms = boardingRelationshipRepository.findBoardingRoomByBoarderHouseId(houseId);

        List<RoomContractDto> results = boardingRooms.stream()
                .map(contractQueryService::getRoomContract)
                .toList();

        log.info("관리 하숙방 리스트 반환: RoomContractDtoCount={}", results.size());

        return results;
    }
}
