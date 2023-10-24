package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public record AddStaticLineSeparatorRequest(@Nullable String adminComments, Integer orderNum) {
    
}
