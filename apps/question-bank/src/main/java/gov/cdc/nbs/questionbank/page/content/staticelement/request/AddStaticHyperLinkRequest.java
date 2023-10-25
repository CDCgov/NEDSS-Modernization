package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public record AddStaticHyperLinkRequest(String label, String linkUrl, @Nullable String adminComments, Long subSectionId) {
    
}
