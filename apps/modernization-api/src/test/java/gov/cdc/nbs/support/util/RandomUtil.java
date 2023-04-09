package gov.cdc.nbs.support.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import gov.cdc.nbs.address.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomUtil {
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtil.class);

    static {
        var randomSeed = RANDOM.nextLong();
        // on test failure, hard code seed to value in failed test run
        // log. Log located at: api/log/spring.log
        RANDOM.setSeed(randomSeed);
        LOGGER.info("Random data generated with seed: " + randomSeed);
    }

    public static void setSeed(long seed) {
        RANDOM.setSeed(seed);
        LOGGER.info("Random seed updated to: " + seed);
    }

    public static int getRandomInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static String getRandomString() {
        return getRandomString(RANDOM.nextInt(5, 20));
    }

    public static <T> T getRandomFromArray(T[] list) {
        var index = RANDOM.nextInt(list.length);
        return list[index];
    }

    public static <T> T getRandomFromArray(List<T> list) {
        var index = RANDOM.nextInt(list.size());
        return list.get(index);
    }

    public static String getRandomString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 126; // ~
        return RANDOM.ints(leftLimit, rightLimit + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    // format 123-456-789
    public static String getRandomSsn() {
        return getRandomNumericString(3) + "-" + getRandomNumericString(3) + "-" + getRandomNumericString(3);
    }

    public static String getRandomPhoneNumber() {
        return getRandomNumericString(10);
    }

    public static String getRandomNumericString(int length) {
        int leftLimit = 48; // 0
        int rightLimit = 57; // 9
        return RANDOM.ints(leftLimit, rightLimit + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static Instant getRandomDateInPast() {
        var oneDayMillis = 86_400_000;
        var nowMillis = Instant.now().toEpochMilli() - oneDayMillis;
        return Instant.ofEpochMilli(RANDOM.nextLong(nowMillis)).atZone(ZoneId.systemDefault()).toInstant()
                .truncatedTo(ChronoUnit.DAYS);
    }

    public static String getRandomStateCode() {
        var index = RANDOM.nextInt(StateCodeUtil.stateCodeMap.size());
        return StateCodeUtil.stateCodeMap.values().toArray(new String[0])[index];
    }

    public static String randomPartialDataSearchString(String data) {
        int len = data.length();
        if (len <= 1) {
            return data;
        }
        return data.substring(0, new Random().nextInt(len - 1) + 1);
    }

    public static Country country() {
        int limit = CountryCodeUtil.countryCodeMap.size();
        int index = RANDOM.nextInt(limit);

        return CountryCodeUtil.countryCodeMap.entrySet().stream().skip(index)
                .map(e -> new Country(e.getValue(), e.getKey()))
                .findFirst()
                .orElse(null);
    }

    public static LocalDate dateInPast() {
        Instant past = getRandomDateInPast();
        return LocalDate.ofInstant(past, ZoneId.systemDefault());
    }
}
