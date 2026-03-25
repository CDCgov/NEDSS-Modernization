package gov.cdc.nbs.questionbank.page.content.tab;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.CreateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class TabCreator {

  private final EntityManager entityManager;
  private final PageContentIdGenerator idGenerator;

  public TabCreator(final EntityManager entityManager, final PageContentIdGenerator idGenerator) {
    this.entityManager = entityManager;
    this.idGenerator = idGenerator;
  }

  public Tab create(long pageId, CreateTabRequest request, Long userId) {
    // Verify the required fields are provided
    if (request == null || !StringUtils.hasLength(request.name())) {
      throw new CreateTabException("Tab Name is required");
    }

    // Find the page
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new CreateTabException("Failed to find page with id: " + pageId);
    }

    // Create the new tab
    WaUiMetadata tab = page.addTab(asAdd(idGenerator.next(), userId, request));

    // Persist the entity
    entityManager.flush();

    return new Tab(tab.getId(), tab.getQuestionLabel(), "T".equals(tab.getDisplayInd()));
  }

  private PageContentCommand.AddTab asAdd(
      String identifier, long userId, CreateTabRequest request) {
    return new PageContentCommand.AddTab(
        request.name(), request.visible(), identifier, userId, Instant.now());
  }
}
