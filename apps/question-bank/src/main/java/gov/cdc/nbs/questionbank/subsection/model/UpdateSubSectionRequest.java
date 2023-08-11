package gov.cdc.nbs.questionbank.subsection.model;

public record UpdateSubSectionRequest(long subSectionId, String questionLabel, String visible) {

}
