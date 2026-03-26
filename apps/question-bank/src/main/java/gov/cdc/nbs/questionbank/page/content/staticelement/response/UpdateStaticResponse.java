package gov.cdc.nbs.questionbank.page.content.staticelement.response;

public sealed interface UpdateStaticResponse {
  Long id();

  String adminComments();

  public record UpdateHyperlink(String label, String linkUrl, Long id, String adminComments)
      implements UpdateStaticResponse {}

  public record UpdateDefault(Long id, String adminComments) implements UpdateStaticResponse {}

  public record UpdateReadOnlyComments(Long id, String commentsText, String adminComments)
      implements UpdateStaticResponse {}
}
