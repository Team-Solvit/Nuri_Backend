package nuri.nuri_server.domain.chat.domain.repository;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class ChatRecordExtraRepositoryImpl implements ChatRecordExtraRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ChatRecord> findLatestMessagesByRoomIds(List<String> roomIds, Pageable pageable) {
        if (CollectionUtils.isEmpty(roomIds)) {
            return Page.empty(pageable);
        }

        Aggregation aggregation = newAggregation(
                match(Criteria.where("roomId").in(roomIds)),
                sort(Sort.by(Sort.Direction.DESC, "createdAt")),
                group("roomId")
                        .first(Aggregation.ROOT).as("latestMessage"),
                replaceRoot("latestMessage"),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize())
        );

        AggregationResults<ChatRecord> results = mongoTemplate.aggregate(
                aggregation,
                ChatRecord.getCollectionName(),
                ChatRecord.class
        );

        return new PageImpl<>(results.getMappedResults(), pageable, roomIds.size());
    }

    @Override
    public long countByRoomIdAndCreatedAtAfter(String roomId, OffsetDateTime lastReadAt) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId));
        query.addCriteria(Criteria.where("createdAt").gt(lastReadAt));
        return mongoTemplate.count(query, ChatRecord.class);
    }
}