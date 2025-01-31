package org.immregistries.smm.tester.manager.query;

import java.util.List;

public abstract class QueryConverter {

  public static QueryConverter getQueryConverter(QueryType queryType) {
    if (queryType == null) {
      return null;
    }
    switch (queryType) {
      case NONE:
        return null;
      case QBP_Z34:
        return new QueryConverterQBPZ34();
      case QBP_Z34_Z44:
        return new QueryConverterQBPZ34Z44();
      case QBP_Z44:
      case QBP:
        return new QueryConverterQBPZ44();
      case VXQ:
        return new QueryConverterVXQ();
    }
    return null;
  }

  public abstract String convert(String message);

  protected static final int MAX_FIELDS_IN_SEGMENT = 50;

  private static int count = 0;

  private static void incCount() {
    count++;
    if (count > 99999) {
      count = 0;
    }
  }

  protected static synchronized int getCount() {
    incCount();
    return count;
  }



  protected static void readFields(List<String> fields, String line) {
    int startPos = 0;
    for (int i = 0; i < MAX_FIELDS_IN_SEGMENT; i++) {
      if (startPos != -1) {
        int endPos = line.indexOf('|', startPos);
        if (endPos == -1) {
          endPos = line.length();
        }
        fields.add(line.substring(startPos, endPos));
        startPos = endPos + 1;
        if (startPos >= line.length()) {
          startPos = -1;
        }
      } else {
        fields.add("");
      }
    }
  }
}
