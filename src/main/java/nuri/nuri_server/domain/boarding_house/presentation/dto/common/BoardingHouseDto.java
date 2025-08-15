package nuri.nuri_server.domain.boarding_house.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import nuri.nuri_server.domain.user.domain.gender.Gender;
import nuri.nuri_server.domain.user.presentation.dto.HostDto;

import java.util.UUID;

@Builder
public record BoardingHouseDto(
        UUID houseId,
        HostDto host,
        String name,
        String location,
        String houseCallNumber,
        String description,
        String nearestStation,
        String nearestSchool,
        Gender gender,
        Boolean isMealProvided
) {
    public static BoardingHouseDto from(BoardingHouseEntity house) {
        HostDto host = HostDto.from(house.getHost());
        return BoardingHouseDto.builder()
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
