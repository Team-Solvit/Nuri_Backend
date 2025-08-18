package nuri.nuri_server.domain.search_boarding_room.domain.repository;

import nuri.nuri_server.domain.search_boarding_room.domain.entity.BoardingRoomDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BoardingRoomElasticSearchRepository extends ElasticsearchRepository<BoardingRoomDocument, String> {
}