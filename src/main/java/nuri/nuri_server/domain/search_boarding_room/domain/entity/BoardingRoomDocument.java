package nuri.nuri_server.domain.search_boarding_room.domain.entity;

import lombok.AccessLevel;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Getter
@Document(indexName = "boarding_room_search", createIndex = true)
@Setting(settingPath = "elasticsearch/boarding_room-setting.json")
@Mapping(mappingPath = "elasticsearch/boarding_room-mapping.json")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardingRoomDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private String roomId;

    @Field(type = FieldType.Keyword)
    private String houseId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Integer)
    private Integer monthlyRent;

    @Field(type = FieldType.Keyword)
    private String region;

    @Field(type = FieldType.Keyword)
    private String gender;

    @Field(type = FieldType.Integer)
    private List<Integer> contractPeriodList;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Long updatedAtEpochMillis;

    @Builder
    public BoardingRoomDocument(String roomId, String houseId, String name, Integer monthlyRent, String region, String gender, List<Integer> contractPeriodList, GeoPoint location, Long updatedAtEpochMillis) {
        this.roomId = roomId;
        this.houseId = houseId;
        this.name = name;
        this.monthlyRent = monthlyRent;
        this.region = region;
        this.gender = gender;
        this.contractPeriodList = contractPeriodList;
        this.location = location;
        this.updatedAtEpochMillis = updatedAtEpochMillis;
    }
}