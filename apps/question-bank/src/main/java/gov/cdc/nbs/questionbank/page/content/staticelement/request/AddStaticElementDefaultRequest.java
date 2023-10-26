package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public record AddStaticElementDefaultRequest(@Nullable String adminComments, Long subSectionId) {
    
}
