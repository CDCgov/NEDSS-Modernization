package gov.cdc.nbs.questionbank.util;

import gov.cdc.nbs.questionbank.exception.NullObjectException;

public class PageBuilderUtil {

  private PageBuilderUtil() {}

  /**
   * Check if an object is null, if so throw an exception with the message "<i>field</i> must not be
   * null"
   *
   * @param <T>
   * @param obj
   * @param field the name of the field to add to the exception message
   * @throws {@link gov.cdc.nbs.questionbank.exception.NullObjectException NullObjectException}
   * @return
   */
  public static <T> T requireNonNull(T obj, String field) {
    if (obj == null) {
      throw new NullObjectException(field + " must not be null");
    }
    return obj;
  }

  /**
   * Check if an String is null or blank, if so throw an exception with the message "<i>field</i>
   * must not be null or empty"
   *
   * @param <T>
   * @param obj
   * @param field the name of the field to add to the exception message
   * @throws {@link gov.cdc.nbs.questionbank.exception.NullObjectException NullObjectException}
   * @return
   */
  public static String requireNotEmpty(String obj, String field) {
    if (obj == null || obj.isBlank()) {
      throw new NullObjectException(field + " must not be null or empty");
    }
    return obj;
  }
}
