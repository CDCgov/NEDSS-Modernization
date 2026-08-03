package gov.cdc.nbs.sql;

import java.util.Collection;
import java.util.stream.Collectors;

public class ParamUtils {

  /*
   * Replace a named query parameter of the style `blah IN (:param)` with the contents directly.
   *
   * We elide the standard parameter replacement here as it creates a ? for every item in a list
   * and with potentially large lists this can exceed the max parameters a query allows.
   */
  public static String replaceListParam(String query, String param, Collection<Long> ints) {
    return query.replace(":" + param, toSqlInContents(ints));
  }

  // For 'IN' queries we want to replace the param with pre-formatted content instead of expanding
  // every item into a `?` placeholder and then having SQL server inject it
  public static String toSqlInContents(Collection<Long> ints) {
    return ints.stream().map(String::valueOf).collect(Collectors.joining(", "));
  }
}
