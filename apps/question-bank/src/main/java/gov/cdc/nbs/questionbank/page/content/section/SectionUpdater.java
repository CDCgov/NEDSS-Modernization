package gov.cdc.nbs.questionbank.page.content.section;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class SectionUpdater {

  private final EntityManager entityManager;

  public SectionUpdater(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Section update(Long pageId, Long sectionId, UpdateSectionRequest request, Long userId) {
    // Verify request is valid
    if (request == null || !StringUtils.hasLength(request.name())) {
      throw new UpdateSectionException("Section Name is required");
    }

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);

    if (page == null) {
      throw new UpdateSectionException("Unable to find page with id: " + pageId);
    }

    WaUiMetadata metadata =
        page.updateSection(
            asCommand(userId, sectionId, request), section -> findSection(section, pageId));

    entityManager.flush();
    return new Section(
        metadata.getId(), metadata.getQuestionLabel(), "T".equals(metadata.getDisplayInd()));
  }

  WaUiMetadata findSection(long sectionId, long pageId) {
    WaUiMetadata metadata = entityManager.find(WaUiMetadata.class, sectionId);
    if (metadata == null
        || metadata.getWaTemplateUid().getId() != pageId
        || metadata.getNbsUiComponentUid() != 1015l) {
      throw new PageContentModificationException("Failed to find section");
    }
    return metadata;
  }

  private PageContentCommand.UpdateSection asCommand(
      Long userId, Long sectionId, UpdateSectionRequest request) {
    return new PageContentCommand.UpdateSection(
        request.name(), request.visible(), sectionId, userId, Instant.now());
  }
}
