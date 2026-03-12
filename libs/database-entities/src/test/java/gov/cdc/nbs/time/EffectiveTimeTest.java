package gov.cdc.nbs.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class EffectiveTimeTest {

  @Test
  void should_create_empty_effective_time() {
    EffectiveTime actual = new EffectiveTime();

    assertNull(actual.getEffectiveToTime());
    assertNull(actual.getEffectiveFromTime());
  }

  @Test
  void should_create_complete_effective_time() {
    LocalDateTime effectiveFromTime = LocalDateTime.parse("2020-03-03T10:15:30");
    LocalDateTime effectiveToTime = LocalDateTime.parse("2020-03-04T10:15:30");
    EffectiveTime actual = new EffectiveTime(effectiveFromTime, effectiveToTime);

    assertEquals(effectiveFromTime, actual.getEffectiveFromTime());
    assertEquals(effectiveToTime, actual.getEffectiveToTime());
  }
}
