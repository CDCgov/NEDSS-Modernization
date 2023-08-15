package gov.cdc.nbs.questionbank.tab.model;

public record UpdateTabRequest(long tabId, String questionLabel, String visible) {

}
