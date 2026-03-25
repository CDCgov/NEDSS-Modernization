package gov.cdc.nbs.support.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

public class RandomUtil {
  private static final Random RANDOM = new Random();
  private static final System.Logger LOGGER = System.getLogger(RandomUtil.class.getName());

  static {
    long randomSeed = RANDOM.nextLong();
    // on test failure, hard code seed to value in failed test run
    // log. Log located at: api/log/spring.log
    RANDOM.setSeed(randomSeed);
    LOGGER.log(System.Logger.Level.INFO, "Random data generated with seed: %d", randomSeed);
  }

  public static String getRandomNumericString(int length) {
    int leftLimit = 48; // 0
    int rightLimit = 57; // 9
    return RANDOM
        .ints(leftLimit, rightLimit + 1)
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
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

    return (flip == 0) ? oneFrom(values) : null;
  }

  public static LocalDate dateInPast() {
    long min = LocalDate.of(1875, Month.JANUARY, 1).toEpochDay();
    long max = LocalDate.now().minusDays(1).toEpochDay();

    long day = RANDOM.nextLong(min, max);
    return LocalDate.ofEpochDay(day);
  }
}
