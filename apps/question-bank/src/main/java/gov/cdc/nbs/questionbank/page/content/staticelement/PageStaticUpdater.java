package gov.cdc.nbs.questionbank.page.content.staticelement;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.UpdateStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.UpdateStaticResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class PageStaticUpdater {

  private final EntityManager entityManager;

  private static final String INVALID_COMPONENT = "could not find component with id ";
  private static final String NOT_STATIC = "component with id ";

  public PageStaticUpdater(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public UpdateStaticResponse.UpdateHyperlink updateHyperlink(
      Long componentId, UpdateStaticRequests.UpdateHyperlink request, Long user) {

    WaUiMetadata staticElement = entityManager.find(WaUiMetadata.class, componentId);

    if (staticElement == null) {
      throw new UpdateStaticElementException(INVALID_COMPONENT + componentId);
    }

    if (staticElement.getNbsUiComponentUid() != 1003L) {
      throw new UpdateStaticElementException(
          NOT_STATIC + componentId + " is not a hyperlink element");
    }

    if (request.label() == null || request.linkUrl() == null) {
      throw new UpdateStaticElementException("label and link are required");
    }

    staticElement.update(
        asUpdateHyperlink(user, request.adminComments(), request.label(), request.linkUrl()));

    return new UpdateStaticResponse.UpdateHyperlink(
        staticElement.getQuestionLabel(),
        staticElement.getDefaultValue(),
        staticElement.getId(),
        staticElement.getAdminComment());
  }

  private PageContentCommand.UpdateHyperlink asUpdateHyperlink(
      long userId, String adminComments, String label, String linkUrl) {
    return new PageContentCommand.UpdateHyperlink(
        userId, adminComments, label, linkUrl, Instant.now());
  }

  @Transactional
  public UpdateStaticResponse.UpdateDefault updateDefaultStaticElement(
      Long componentId, UpdateStaticRequests.UpdateDefault request, Long user) {

    WaUiMetadata staticElement = entityManager.find(WaUiMetadata.class, componentId);

    if (staticElement == null) {
      throw new UpdateStaticElementException(INVALID_COMPONENT + componentId);
    }

    if (staticElement.getNbsUiComponentUid() != 1012L
        && staticElement.getNbsUiComponentUid() != 1030L
        && staticElement.getNbsUiComponentUid() != 1036L) {
      throw new UpdateStaticElementException(NOT_STATIC + componentId + " is not static");
    }

    staticElement.update(asUpdateDefaultStaticElement(user, request.adminComments()));

    return new UpdateStaticResponse.UpdateDefault(
        staticElement.getId(), staticElement.getAdminComment());
  }

  private PageContentCommand.UpdateDefaultStaticElement asUpdateDefaultStaticElement(
      long userId, String adminComments) {
    return new PageContentCommand.UpdateDefaultStaticElement(userId, adminComments, Instant.now());
  }

  @Transactional
  public UpdateStaticResponse.UpdateReadOnlyComments updateReadOnlyComments(
      Long componentId, UpdateStaticRequests.UpdateReadOnlyComments request, Long user) {

    WaUiMetadata staticElement = entityManager.find(WaUiMetadata.class, componentId);

    if (staticElement == null) {
      throw new UpdateStaticElementException(INVALID_COMPONENT + componentId);
    }

    if (staticElement.getNbsUiComponentUid() != 1014L) {
      throw new UpdateStaticElementException(
          NOT_STATIC + componentId + " is not a read only comments element");
    }

    if (request.commentsText() == null) {
      throw new UpdateStaticElementException("comments text is required");
    }

    staticElement.update(
        asUpdateReadOnlyComments(user, request.adminComments(), request.commentsText()));

    return new UpdateStaticResponse.UpdateReadOnlyComments(
        staticElement.getId(), staticElement.getQuestionLabel(), staticElement.getAdminComment());
  }

  private PageContentCommand.UpdateReadOnlyComments asUpdateReadOnlyComments(
      Long userId, String adminComments, String comments) {
    return new PageContentCommand.UpdateReadOnlyComments(
        userId, comments, adminComments, Instant.now());
  }
}
