package gov.cdc.nbs.questionbank.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gov.cdc.nbs.questionbank.exception.NullObjectException;
import org.junit.jupiter.api.Test;

class PageBuilderUtilTest {

  @Test
  void should_throw_null_object() {
    assertThrows(
        NullObjectException.class, () -> PageBuilderUtil.requireNonNull(null, "Some message"));
  }

  @Test
  void should_set_message() {
    final String field = "field";
    try {
      PageBuilderUtil.requireNonNull(null, field);
    } catch (NullObjectException ex) {
      assertEquals(field + " must not be null", ex.getMessage());
    }
  }

  @Test
  void should_not_throw_null_object() {
    String value = PageBuilderUtil.requireNonNull("Not null", "Some message");
    assertEquals("Not null", value);
  }

  @Test
  void should_not_allow_empty_string() {
    String value = " ";
    assertThrows(NullObjectException.class, () -> PageBuilderUtil.requireNotEmpty(value, "field"));
  }

  @Test
  void should_not_allow_null_string() {
    String value = null;
    assertThrows(NullObjectException.class, () -> PageBuilderUtil.requireNotEmpty(value, "field"));
  }

  @Test
  void should_allow_string() {
    String value = "test";
    assertEquals("test", PageBuilderUtil.requireNotEmpty(value, "field"));
  }

  @Test
  void should_set_not_empty_message() {
    final String field = "field";
    try {
      PageBuilderUtil.requireNotEmpty(null, field);
    } catch (NullObjectException ex) {
      assertEquals(field + " must not be null or empty", ex.getMessage());
    }
  }
}
