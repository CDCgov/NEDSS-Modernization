package gov.cdc.nbs.questionbank.section.model;

public record UpdateSectionRequest(long sectionId, String questionLabel, String visible) {

}
