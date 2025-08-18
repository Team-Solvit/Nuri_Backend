package nuri.nuri_server.domain.chat.domain.repository;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class ChatRecordExtraRepositoryImpl implements ChatRecordExtraRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<ChatRecord> findLatestMessagesByRoomIds(List<String> roomIds) {
        if (CollectionUtils.isEmpty(roomIds)) {
            return Collections.emptyList();
        }

        Aggregation aggregation = newAggregation(
                match(Criteria.where("roomId").in(roomIds)),
                sort(Sort.by(Sort.Direction.DESC, "createdAt")),
                group("roomId")
                        .first(Aggregation.ROOT).as("latestMessage"),
                replaceRoot("latestMessage")
        );

        AggregationResults<ChatRecord> results = mongoTemplate.aggregate(
                aggregation,
                ChatRecord.getCollectionName(),
                ChatRecord.class
        );

        return results.getMappedResults();
    }
}