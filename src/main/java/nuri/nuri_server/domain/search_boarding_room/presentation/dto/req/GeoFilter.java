package nuri.nuri_server.domain.search_boarding_room.presentation.dto.req;

public record GeoFilter(
        Float lat,
        Float lon,
        String radiusMeters
) {
    public Boolean validate() {
        return lat != null && lon != null && radiusMeters != null;
    }
}