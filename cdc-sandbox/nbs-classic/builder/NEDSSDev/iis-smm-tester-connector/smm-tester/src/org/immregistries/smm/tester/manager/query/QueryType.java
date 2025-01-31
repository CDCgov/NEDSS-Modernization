package org.immregistries.smm.tester.manager.query;

public enum QueryType {
  NONE("None"),
  QBP_Z34("QBP-Z34", "QBP Z34"),
  QBP_Z34_Z44("QBP-Z34-Z44", "QBP Z44+H44"),
  QBP_Z44("QBP-Z44", "QBP Z44"),
  QBP("QBP"),
  VXQ("VXQ");

  private String[] label;

  private QueryType(String... label) {
    this.label = label;
  }

  public static QueryType getValue(String label) {
    for (QueryType queryType : values()) {
      for (String l : queryType.label) {
        if (l.equals(label)) {
          return queryType;
        }
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return label[0];
  }
}
