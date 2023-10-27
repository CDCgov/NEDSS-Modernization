package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public interface PageStaticRequests {

    String adminComments();

    Long subSectionId();

    public record AddStaticElementDefaultRequest(
            @Nullable String adminComments,
            Long subSectionId) {
    }

    public record AddStaticHyperLinkRequest(
            String label,
            String linkUrl,
            @Nullable String adminComments,
            Long subSectionId) {
    }

    public record AddStaticReadOnlyCommentsRequest(
            String commentsText,
            @Nullable String adminComments,
            Long subSectionId) {
    }

}
