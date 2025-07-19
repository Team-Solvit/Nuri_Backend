package nuri.nuri_server.global.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;

public class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {
    @Override
    public Date convert(@NonNull OffsetDateTime source) {
        return Date.from(source.toInstant());
    }
}