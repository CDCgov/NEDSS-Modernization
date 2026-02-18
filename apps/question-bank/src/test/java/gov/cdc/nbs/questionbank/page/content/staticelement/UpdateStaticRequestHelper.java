package gov.cdc.nbs.questionbank.page.content.staticelement;

import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;

public class UpdateStaticRequestHelper {

  public static UpdateStaticRequests.UpdateHyperlink withLabel(
      UpdateStaticRequests.UpdateHyperlink request, String label) {
    return new UpdateStaticRequests.UpdateHyperlink(
        label, request.linkUrl(), request.adminComments());
  }

  public static UpdateStaticRequests.UpdateHyperlink withLink(
      UpdateStaticRequests.UpdateHyperlink request, String link) {
    return new UpdateStaticRequests.UpdateHyperlink(request.label(), link, request.adminComments());
  }

  public static UpdateStaticRequests.UpdateHyperlink withHyperlinkAdmin(
      UpdateStaticRequests.UpdateHyperlink request, String adminComments) {
    return new UpdateStaticRequests.UpdateHyperlink(
        request.label(), request.linkUrl(), adminComments);
  }

  public static UpdateStaticRequests.UpdateReadOnlyComments withComments(
      UpdateStaticRequests.UpdateReadOnlyComments request, String comments) {
    return new UpdateStaticRequests.UpdateReadOnlyComments(comments, request.adminComments());
  }

  public static UpdateStaticRequests.UpdateReadOnlyComments withReadOnlyCommentsAdminComments(
      UpdateStaticRequests.UpdateReadOnlyComments request, String comments) {
    return new UpdateStaticRequests.UpdateReadOnlyComments(request.commentsText(), comments);
  }
}
