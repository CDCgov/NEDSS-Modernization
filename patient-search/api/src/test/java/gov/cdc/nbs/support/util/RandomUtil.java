package gov.cdc.nbs.support.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomUtil {
    private static Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(RandomUtil.class);
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

    public static String getRandomString() {
        return getRandomString(random.nextInt(5, 20));
    }

    public static <T> T getRandomFromArray(T[] list) {
        var index = random.nextInt(list.length);
        return list[index];
    }

    public static <T> T getRandomFromArray(List<T> list) {
        var index = random.nextInt(list.size());
        return list.get(index);
    }

    public static String getRandomString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 126; // ~
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

    public static String getRandomStateCode() {
        var index = random.nextInt(StateCodeUtil.stateCodeMap.size());
        return StateCodeUtil.stateCodeMap.values().toArray(new String[0])[index];
    }

    public static String randomPartialDataSearchString(String data) {
        int random = new Random().nextInt(3);
        int len = data.length();
        if (len<=1) {
            return data;
        }
        // make sure prefix, suffix, and interior substring are tested evenly
        switch(random) {
            case 0: // prefix
                return data.substring(0, 1);
            case 1: // suffix
                return data.substring(len-1, len);
            case 2: // interior substring
                return data.substring(1, len-1);
        }
        return data;
    }
}
