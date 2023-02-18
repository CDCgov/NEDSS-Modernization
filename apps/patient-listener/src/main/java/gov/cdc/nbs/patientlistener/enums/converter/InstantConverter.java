package gov.cdc.nbs.patientlistener.enums.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;


public class InstantConverter implements PropertyValueConverter {

    private static final List<DateTimeFormatter> formats = new ArrayList<>();
    private static final DateTimeFormatter writer = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneOffset.UTC);

    static {
        formats.add(new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("M/d/[uuuu][uu]"))
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter());
        formats.add(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S"));
        formats.add(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SS"));
        formats.add(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS"));
        formats.add(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS"));
        formats.add(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"));
    }

    /**
     * Attempts to convert an Instant to a String
     */
    @Override
    public Object write(Object value) {
        if (value instanceof Instant instant) {
            return writer.format(instant);
        } else {
            return value;
        }
    }

    /**
     * Attempts to convert a String to an Instant
     */
    @Override
    public Object read(Object value) {
        if (value instanceof String s) {
            if (s.startsWith("StringValue{value='")) {
                s = s.substring("StringValue{value='".length(), s.length() - 2);
            }
            for (var format : formats) {
                try {
                    return LocalDateTime.parse(s, format).toInstant(ZoneOffset.UTC);
                } catch (DateTimeParseException e) {
                    // ignore exception until all formats have been tried
                }
            }
            throw new ConversionException("Failed to convert String to Instant: " + s);
        } else {
            return value;
        }
    }

}