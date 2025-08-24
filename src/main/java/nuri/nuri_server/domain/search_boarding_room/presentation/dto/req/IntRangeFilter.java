package nuri.nuri_server.domain.search_boarding_room.presentation.dto.req;

public record IntRangeFilter(
    Integer min,
    Integer max
) {
    public Boolean validate() {
        return min != null && max != null;
    }
}