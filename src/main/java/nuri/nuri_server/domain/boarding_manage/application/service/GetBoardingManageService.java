package nuri.nuri_server.domain.boarding_manage.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.boarding_manage.domain.entity.BoardingManageWorkEntity;
import nuri.nuri_server.domain.boarding_manage.domain.repository.BoardingManageWorkRepository;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.common.BoardingManageWorkDto;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.common.BoardingRelationshipDto;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.req.BoardingManageWorkReadRequestDto;
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
    private final BoardingManageWorkRepository boardingManageWorkRepository;
    private final ThirdPartyRepository thirdPartyRepository;
    private final ContractQueryService contractQueryService;
    private final BoardingManageQueryService boardingManageQueryService;

    @Transactional(readOnly = true)
    public List<BoardingHouseDto> getManageBoardingHouseList(NuriUserDetails nuriUserDetails) {
        UUID thirdPartyId = nuriUserDetails.getId();
        log.info("관리 하숙집 리스트 요청: thirdPartyId={}", thirdPartyId);

        validateThirdPartyId(thirdPartyId);

        List<BoardingRelationshipEntity> relationships = boardingRelationshipRepository.findAllByThirdPartyId(thirdPartyId);

        log.info("관리 하숙집 리스트 반환: relationshipCount={}", relationships.size());
        return relationships.stream()
                .map(relationship -> BoardingHouseDto.from(relationship.getBoardingHouse()))
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

    @Transactional(readOnly = true)
    public List<BoardingManageWorkDto> getBoardingManageWork(NuriUserDetails nuriUserDetails, BoardingManageWorkReadRequestDto workReadRequestDto) {
        UUID thirdPartyId = nuriUserDetails.getId();
        log.info("하숙관리 업무 조회 요청: thirdPartyId={}", thirdPartyId);

        validateThirdPartyId(thirdPartyId);

        List<BoardingManageWorkEntity> workEntities;
        if(workReadRequestDto.houseId() == null)
            workEntities = boardingManageWorkRepository.findAllByThirdPartyIdAndDate(thirdPartyId, workReadRequestDto.date());
        else
            workEntities = boardingManageWorkRepository.findAllByThirdPartyIdAndHouseIdAndDate(thirdPartyId, workReadRequestDto.houseId(), workReadRequestDto.date());

        List<BoardingManageWorkDto> results = workEntities.stream()
                .map(boardingManageQueryService::getBoardingManageWorkDto)
                .toList();

        log.info("하숙관리 업무 조회 반환: workCount={}", results.size());
        return results;
    }
}
