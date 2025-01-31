package org.immregistries.smm.tester.manager.query;

public enum QueryResponseType {
  MATCH("Match"),
  LIST("List"),
  NOT_FOUND("Not Found"),
  ERROR("Error"),
  TOO_MANY("Too Many"),
  PROFILE_ID_MISSING("Profile Id is Missing"),
  PROFILE_ID_UNEXPECTED("Profile Id is Unexpected");

  private String label;

  public String getLabel() {
    return label;
  }

  private QueryResponseType(String label) {
    this.label = label;
  }



}
