package nuri.nuri_server.domain.boarding_house.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import nuri.nuri_server.domain.boarding_house.domain.gender.Gender;
import nuri.nuri_server.domain.user.presentation.dto.HostInfo;

import java.util.UUID;

@Builder
public record BoardingHouseInfo(
        UUID houseId,
        HostInfo host,
        String name,
        String location,
        String houseCallNumber,
        String description,
        String nearestStation,
        String nearestSchool,
        Gender gender,
        Boolean isMealProvided
) {
    public static BoardingHouseInfo from(BoardingHouseEntity house) {
        HostInfo host = HostInfo.from(house.getHost());
        return BoardingHouseInfo.builder()
                .houseId(house.getId())
                .host(host)
                .name(house.getName())
                .location(house.getLocation())
                .houseCallNumber(house.getHouseCallNumber())
                .description(house.getDescription())
                .nearestStation(house.getNearestStation())
                .nearestSchool(house.getNearestSchool())
                .gender(house.getGender())
                .isMealProvided(house.getIsMealProvided())
                .build();
    }
}
