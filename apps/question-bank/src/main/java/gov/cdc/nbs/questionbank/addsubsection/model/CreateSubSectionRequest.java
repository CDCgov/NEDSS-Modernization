package gov.cdc.nbs.questionbank.addsubsection.model;

public record CreateSubSectionRequest(long sectionId, long page, String name, String visible) {

}
