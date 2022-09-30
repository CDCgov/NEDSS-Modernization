package gov.cdc.nbs.support;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtil {
    private static Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(TestUtil.class);
    static {
        var randomSeed = random.nextLong();
        // on test failure, hard code seed to value in failed test run
        // log. Log located at: api/log/spring.log
        random.setSeed(randomSeed);
        logger.info("Random data generated with seed: " + randomSeed);
    }

    public static void setSeed(long seed) {
        random.setSeed(seed);
        logger.info("Random seed updated to: " + seed);
    }

    public static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }

    private static String[] states = new String[] {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida",
            "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine",
            "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
            "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"

    };

    public static String getRandomString() {
        return getRandomString(random.nextInt(5, 20));
    }

    public static <T> T getRandomFromArray(T[] list) {
        var index = random.nextInt(list.length);
        return list[index];
    }

    public static String getRandomString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 122; // z
        return random.ints(leftLimit, rightLimit + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    // format 123-456-789
    public static String getRandomSsn() {
        return getRandomNumericString(3) + "-" + getRandomNumericString(3) + "-" + getRandomNumericString(3);
    }

    public static String getRandomPhoneNumber() {
        // format 123-456-7890
        return getRandomNumericString(3) + "-" + getRandomNumericString(3) + "-" + getRandomNumericString(4);
    }

    public static String getRandomNumericString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 57; // 9
        return random.ints(leftLimit, rightLimit + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static Instant getRandomDateInPast() {
        var oneDayMillis = 86_400_000;
        var nowMillis = Instant.now().toEpochMilli() - oneDayMillis;
        return Instant.ofEpochMilli(random.nextLong(nowMillis)).atZone(ZoneId.systemDefault()).toInstant()
                .truncatedTo(ChronoUnit.DAYS);
    }

    public static String getRandomState() {
        var index = random.nextInt(states.length);
        return states[index];
    }
}
