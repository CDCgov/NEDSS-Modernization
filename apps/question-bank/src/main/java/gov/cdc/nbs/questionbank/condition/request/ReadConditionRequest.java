package gov.cdc.nbs.questionbank.condition.request;

public record ReadConditionRequest(String searchText, boolean excludeInUse) {
  public ReadConditionRequest() {
    this("", true);
  }
}
