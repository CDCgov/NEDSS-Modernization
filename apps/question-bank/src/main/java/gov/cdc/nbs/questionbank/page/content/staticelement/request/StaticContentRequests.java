package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public sealed interface StaticContentRequests {

        String adminComments();

        Long subSectionId();

        public record AddDefault(
                        @Nullable String adminComments,
                        Long subSectionId) implements StaticContentRequests {
        }

        public record UpdateDefault(
                        @Nullable String adminComments,
                        @Nullable Long subSectionId) implements StaticContentRequests {
        }

        public record AddHyperlink(
                        String label,
                        String linkUrl,
                        @Nullable String adminComments,
                        Long subSectionId) implements StaticContentRequests {
        }

        public record UpdateHyperlink(
                        @Nullable String label,
                        @Nullable String linkUrl,
                        @Nullable String adminComments,
                        @Nullable Long subSectionId) implements StaticContentRequests {
        }

        public record AddReadOnlyComments(
                        String commentsText,
                        @Nullable String adminComments,
                        Long subSectionId) implements StaticContentRequests {
        }

        public record UpdateReadOnlyComments(
                        @Nullable String commentsText,
                        @Nullable String adminComments,
                        @Nullable Long subSectionId) implements StaticContentRequests {}

}
