package gov.cdc.nbs.questionbank.page.content.subsection.request;

public record OrderSubSectionRequest(Long subSectionId, Integer tabPosition, Integer sectionPosition,
                                     Integer  currentPosition, Integer desiredPosition) {

}
