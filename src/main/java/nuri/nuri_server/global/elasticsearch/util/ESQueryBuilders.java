package nuri.nuri_server.global.elasticsearch.util;

import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import nuri.nuri_server.domain.search_boarding_room.presentation.dto.req.GeoFilter;
import nuri.nuri_server.domain.search_boarding_room.presentation.dto.req.IntRangeFilter;
import org.springframework.stereotype.Component;

@Component
public class ESQueryBuilders {
    public Query textMatch(String field, String text) {
        return MatchQuery.of(m -> m
                .field(field)
                .query(text)
        )._toQuery();
    }

    public Query textFuzzyMatch(String field, String text) {
        return MatchQuery.of(m -> m
                .field(field)
                .query(text)
                .fuzziness("AUTO")
        )._toQuery();
    }

    public Query textPrefixMatch(String field, String text) {
        return WildcardQuery.of(w -> w
                .field(field + ".keyword")
                .value("*" + text + "*")
                .caseInsensitive(true)
        )._toQuery();
    }

    public Query geoDistance(String field, GeoFilter geoFilter, float boost) {
        return GeoDistanceQuery.of(g -> g
                .field(field)
                .location(l -> l.latlon(LatLonGeoLocation.of(ll -> ll
                        .lat(geoFilter.lat())
                        .lon(geoFilter.lon())
                )))
                .distance(geoFilter.radiusMeters())
                .boost(boost)
        )._toQuery();
    }

    public Query integerRange(String field, IntRangeFilter intRangeFilter) {
        return new RangeQuery.Builder().number(
                n -> n
                        .field(field)
                        .gte(intRangeFilter.min().doubleValue())
                        .lte(intRangeFilter.max().doubleValue())
        ).build()._toQuery();
    }

    public Query term(String field, String term) {
        return TermQuery.of(t -> t
                .field(field)
                .value(term)
        )._toQuery();
    }

}
