package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.SubSection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class SubSectionUpdater {

  private final EntityManager entityManager;

  public SubSectionUpdater(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public SubSection update(
      Long pageId, Long subSectionId, UpdateSubSectionRequest request, Long userId) {
    if (request == null || !StringUtils.hasLength(request.name())) {
      throw new UpdateSubSectionException("SubSection Name is required");
    }

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);

    if (page == null) {
      throw new UpdateSubSectionException("Unable to find page with id: " + pageId);
    }

    WaUiMetadata section =
        page.updateSubSection(
            asCommand(userId, subSectionId, request),
            subsectionId -> findSubsection(subsectionId, pageId));

    entityManager.flush();
    return new SubSection(
        section.getId(), section.getQuestionLabel(), "T".equals(section.getDisplayInd()));
  }

  WaUiMetadata findSubsection(long subsectionId, long pageId) {
    WaUiMetadata metadata = entityManager.find(WaUiMetadata.class, subsectionId);
    if (metadata == null
        || metadata.getWaTemplateUid().getId() != pageId
        || metadata.getNbsUiComponentUid() != 1016l) {
      throw new PageContentModificationException("Failed to find subsection");
    }
    return metadata;
  }

  private PageContentCommand.UpdateSubsection asCommand(
      Long userId, long subSectionId, UpdateSubSectionRequest request) {
    return new PageContentCommand.UpdateSubsection(
        request.name(), request.visible(), subSectionId, userId, Instant.now());
  }
}
