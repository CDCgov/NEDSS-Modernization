package gov.cdc.nbs.questionbank.page.content.section;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.section.exception.CreateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class SectionCreator {

  private final EntityManager entityManager;
  private final PageContentIdGenerator idGenerator;

  public SectionCreator(
      final EntityManager entityManager, final PageContentIdGenerator idGenerator) {
    this.entityManager = entityManager;
    this.idGenerator = idGenerator;
  }

  public Section create(long pageId, CreateSectionRequest request, Long userId) {
    // Verify the required fields are provided
    if (request == null || !StringUtils.hasLength(request.name())) {
      throw new CreateSectionException("Section Name is required");
    }

    // Find the page
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new CreateSectionException("Failed to find page with id: " + pageId);
    }

    // Create the new section
    WaUiMetadata section =
        page.addSection(asAdd(idGenerator.next(), userId, request.tabId(), request));

    // Persist the entities
    entityManager.flush();

    return new Section(
        section.getId(), section.getQuestionLabel(), "T".equals(section.getDisplayInd()));
  }

  private PageContentCommand.AddSection asAdd(
      String sectionId, Long userId, Long tab, CreateSectionRequest request) {
    return new PageContentCommand.AddSection(
        request.name(), request.visible(), sectionId, tab, userId, Instant.now());
  }
}
