package gov.cdc.nbs.questionbank.page.content.staticelement.request;

import org.springframework.lang.Nullable;

public sealed interface UpdateStaticRequests {
  String adminComments();

  public record UpdateReadOnlyComments(
      @Nullable String commentsText, @Nullable String adminComments)
      implements UpdateStaticRequests {}

  public record UpdateHyperlink(String label, String linkUrl, @Nullable String adminComments)
      implements UpdateStaticRequests {}

  public record UpdateDefault(@Nullable String adminComments) implements UpdateStaticRequests {}
}
