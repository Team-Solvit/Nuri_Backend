package nuri.nuri_server.domain.search_boarding_room.application.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomQueryService;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.search_boarding_room.domain.entity.BoardingRoomDocument;
import nuri.nuri_server.domain.search_boarding_room.presentation.dto.req.BoardingRoomSearchFilterDto;
import nuri.nuri_server.global.elasticsearch.util.ESQueryBuilders;
import nuri.nuri_server.global.properties.PageSizeProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchBoardingRoomService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final BoardingRoomRepository boardingRoomRepository;
    private final ESQueryBuilders esQueryBuilders;
    private final PageSizeProperties pageSizeProperties;
    private final BoardingRoomQueryService boardingRoomQueryService;

    @Transactional(readOnly = true)
    public List<BoardingRoomDto> searchBoardingRoom(BoardingRoomSearchFilterDto searchFilterDto) {
        log.info("하숙방 검색 요청 : boardingRoomSearchRequest={}", searchFilterDto);

        Query finalQuery = getBoardingRoomSearchFilter(searchFilterDto);

        List<SortOptions> sortOptions = getBoardingRoomSearchSortOptions();

        Integer size = pageSizeProperties.getBoardingRoomSearch();
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(finalQuery)
                .withSort(sortOptions)
                .withPageable(size != null ? PageRequest.of(searchFilterDto.start(), size) : Pageable.unpaged())
                .build();

        SearchHits<BoardingRoomDocument> searchHits = elasticsearchOperations.search(searchQuery, BoardingRoomDocument.class);

        List<BoardingRoomDto> results = toBoardingRoomDtoList(searchHits);

        log.info("하숙방 검색 반환 : BoardingRoomDtoListCount={}", results.size());
        return results;
    }

    private List<BoardingRoomDto> toBoardingRoomDtoList(SearchHits<BoardingRoomDocument> searchHits) {
        List<UUID> roomIds = searchHits.getSearchHits().stream()
                .map(hit -> UUID.fromString(hit.getContent().getRoomId()))
                .toList();

        Map<UUID, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < roomIds.size(); i++) {
            orderMap.put(roomIds.get(i), i);
        }

        List<BoardingRoomEntity> rooms = boardingRoomRepository.findAllById(roomIds);

        rooms.sort(Comparator.comparingInt(r -> orderMap.getOrDefault(r.getId(), Integer.MAX_VALUE)));

        return rooms.stream()
                .map(boardingRoomQueryService::getBoardingRoomDto)
                .toList();
    }

    private List<SortOptions> getBoardingRoomSearchSortOptions() {
        return List.of(
                SortOptions.of(s -> s.score(sc -> sc.order(SortOrder.Desc))),
                SortOptions.of(s -> s.field(f -> f.field("updatedAtEpochMillis").order(SortOrder.Desc)))
        );
    }

    private Query getBoardingRoomSearchFilter(BoardingRoomSearchFilterDto searchFilterDto) {
        List<Query> mustQueries = new ArrayList<>();
        List<Query> shouldQueries = new ArrayList<>();

        // 지역 필터
        Optional.ofNullable(searchFilterDto.region())
                .ifPresent(region -> mustQueries.add(
                        esQueryBuilders.textMatch("region", region)
                ));

        // 역 필터
        if(searchFilterDto.station() != null && searchFilterDto.station().validate())
            shouldQueries.add(esQueryBuilders.geoDistance("location", searchFilterDto.station(), 2.0f));

        // 학교 필터
        if(searchFilterDto.school() != null && searchFilterDto.school().validate())
            shouldQueries.add(esQueryBuilders.geoDistance("location", searchFilterDto.school(), 2.0f));

        // 가격 필터
        if(searchFilterDto.price() != null && searchFilterDto.price().validate())
            mustQueries.add(esQueryBuilders.integerRange("monthlyRent", searchFilterDto.price()));

        // 기간 필터
        if(searchFilterDto.contractPeriod() != null && searchFilterDto.contractPeriod().validate())
            mustQueries.add(esQueryBuilders.integerRange("contractPeriodList", searchFilterDto.contractPeriod()));

        // 성별 필터
        Optional.ofNullable(searchFilterDto.gender())
                .ifPresent(gender -> mustQueries.add(
                        esQueryBuilders.term("gender", gender.name())
                ));

        // 검색어 필터
        Optional.ofNullable(searchFilterDto.name())
                .ifPresent(name -> {
                    shouldQueries.add(
                            esQueryBuilders.textFuzzyMatch("name", name)
                    );
                    shouldQueries.add(
                            esQueryBuilders.textPrefixMatch("name", name)
                    );
                });

        BoolQuery.Builder boolQuery = new BoolQuery.Builder().must(mustQueries);
        if (!shouldQueries.isEmpty())
            // should 쿼리중에 하나라도 일치해야함
            boolQuery.should(shouldQueries).minimumShouldMatch("1");

        return boolQuery.build()._toQuery();
    }
}
