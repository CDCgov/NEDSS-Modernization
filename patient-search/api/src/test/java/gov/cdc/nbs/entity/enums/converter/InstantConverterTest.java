package gov.cdc.nbs.entity.enums.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class InstantConverterTest {
    private final InstantConverter converter = new InstantConverter();

    @Test
    void parseFormat1() throws ParseException {
        String date = "12/30/2022";
        var instant = converter.read(date);
        assertNotNull(instant);
    }

    @Test
    void parseFormat2() throws ParseException {
        String date = "2022-11-18 22:27:13.302";
        var instant = converter.read(date);
        assertNotNull(instant);
    }

    @Test
    void parseFormat3() throws ParseException {
        String date = "2022-11-18 22:27:13.83";
        var instant = converter.read(date);
        assertNotNull(instant);
    }

    @Test
    void parseFormat4() throws ParseException {
        String date = "2022-11-18 22:27:13.8";
        var instant = converter.read(date);
        assertNotNull(instant);
    }

    @Test
    void testWrite() {
        var now = Instant.now();
        var output = converter.write(now);
        assertNotNull(output);
    }

    @Test
    void readOutput() {
        // write value as String
        var now = Instant.now();
        var output = converter.write(now);
        assertNotNull(output);
        // parse String back to Instant
        var instant = (Instant) converter.read(output);
        assertNotNull(instant);

        // Verify Instant matches original
        LocalDateTime original = LocalDateTime.ofInstant(now, ZoneOffset.UTC);
        LocalDateTime parsed = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        assertEquals(original.getDayOfYear(), parsed.getDayOfYear());
        assertEquals(original.getHour(), parsed.getHour());
        assertEquals(original.getHour(), parsed.getHour());
        assertEquals(original.getMinute(), parsed.getMinute());
        assertEquals(original.getSecond(), parsed.getSecond());
    }
}
