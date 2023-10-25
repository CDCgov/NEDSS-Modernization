package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public record AddStaticReadOnlyCommentsRequest(String comments, @Nullable String adminComments, Long subSectionId) {
    
}
