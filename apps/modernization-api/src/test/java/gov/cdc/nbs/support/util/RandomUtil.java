package gov.cdc.nbs.support.util;

import gov.cdc.nbs.message.enums.Deceased;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

public class RandomUtil {
  private static final Random RANDOM = new Random();
  private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtil.class);

  static {
    var randomSeed = RANDOM.nextLong();
    // on test failure, hard code seed to value in failed test run
    // log. Log located at: api/log/spring.log
    RANDOM.setSeed(randomSeed);
    LOGGER.info("Random data generated with seed: {}", randomSeed);
  }

  public static void setSeed(long seed) {
    RANDOM.setSeed(seed);
    LOGGER.info("Random seed updated to: {}", seed);
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

  public static String getRandomSsn() {
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
    return data.substring(0, RANDOM.nextInt(len - 1));
  }

  @SafeVarargs
  public static <T> T oneFrom(T... values) {
    var index = RANDOM.nextInt(values.length);
    return values[index];
  }

  @SafeVarargs
  public static <T> T maybeOneFrom(T... values) {
    int flip = RANDOM.nextInt(2);

    return (flip == 0)
        ? oneFrom(values)
        : null;
  }

  public static Deceased deceased() {
    return RandomUtil.oneFrom(Deceased.Y, Deceased.N, Deceased.UNK);
  }

  public static String ethnicity() {
    return RandomUtil.oneFrom(
        "2135-2",   // Hispanic
        "2186-5",           // Non-Hispanic
        "UNK"               // Unknown
    );
  }

  public static String gender() {
    return oneFrom("U", "M", "F");
  }

  public static String maybeGender() {
    return maybeOneFrom("U", "M", "F");
  }

  public static String indicator() {
    return oneFrom("N", "Y", "UNK");
  }

  public static String maybeIndicator() {
    return maybeOneFrom("N", "Y", "UNK");
  }

  public static String country() {
    int limit = CountryCodeUtil.countryCodeMap.size();
    int index = RANDOM.nextInt(limit);

    return CountryCodeUtil.countryCodeMap.values()
        .stream()
        .skip(index)
        .findFirst()
        .orElse(null);
  }

  public static LocalDate dateInPast() {
    Instant past = getRandomDateInPast();
    return LocalDate.ofInstant(past, ZoneOffset.UTC);
  }
}
