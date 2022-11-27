package gov.cdc.nbs.entity.enums.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

public class InstantConverter implements PropertyValueConverter {

    private static final List<DateTimeFormatter> formats = new ArrayList<>();
    private static final DateTimeFormatter writer = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneOffset.UTC);

    static {
        formats.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        formats.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        formats.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"));
        formats.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Override
    public Object write(Object value) {
        if (value instanceof Instant instant) {
            return writer.format(instant);
        } else {
            return value;
        }
    }

    @Override
    public Object read(Object value) {
        if (value instanceof String s) {
            for (var format : formats) {
                try {
                    var parsed = LocalDateTime.parse((String) value, format).toInstant(ZoneOffset.UTC);
                    return parsed;
                } catch (DateTimeParseException e) {
                    // ignore exception until all formats have been tried
                }
            }
            throw new RuntimeException("Failed to convert String to Instant: " + s);

        } else {
            return value;
        }
    }

}
