package nuri.nuri_server.domain.search_boarding_room.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.search_boarding_room.domain.entity.BoardingRoomDocument;
import nuri.nuri_server.domain.search_boarding_room.presentation.dto.common.GeoPointDto;
import nuri.nuri_server.global.feign.google_map.GeoApiService;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardingRoomDocumentQueryService {

    private final GeoApiService geoApiService;

    public BoardingRoomDocument toBoardingRoomDocument(BoardingRoomEntity room, BoardingHouseEntity house, List<Integer> contractPeriodList) {
        String address = house.getLocation();
        String region = geoApiService.getRegionFromAddress(address);

        GeoPointDto geoPointDto = geoApiService.getGeoPointFromAddress(address);
        GeoPoint geoPoint = geoPointDto.toElasticsearchGeoPoint();

        Long updatedAtEpochMillis = room.getUpdatedAt()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        return BoardingRoomDocument.builder()
                .roomId(room.getId().toString())
                .houseId(house.getId().toString())
                .name(room.getName())
                .monthlyRent(room.getMonthlyRent())
                .region(region)
                .gender(house.getGender().name())
                .contractPeriodList(contractPeriodList)
                .location(geoPoint)
                .updatedAtEpochMillis(updatedAtEpochMillis)
                .build();
    }
}
