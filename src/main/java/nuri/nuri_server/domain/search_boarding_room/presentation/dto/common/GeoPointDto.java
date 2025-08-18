package nuri.nuri_server.domain.search_boarding_room.presentation.dto.common;

import lombok.Builder;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Builder
public record GeoPointDto(
        Double lat,
        Double lon
) {
    public GeoPoint toElasticsearchGeoPoint() {
        return new GeoPoint(lat, lon);
    }
}
