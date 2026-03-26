package gov.cdc.nbs.questionbank.page.content.tab;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class TabUpdater {

  private final EntityManager entityManager;

  public TabUpdater(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Tab update(Long pageId, Long tab, UpdateTabRequest request, long user) {
    if (request == null || !StringUtils.hasLength(request.name())) {
      throw new UpdateTabException("Tab Name is required");
    }

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);

    if (page == null) {
      throw new UpdateTabException("Unable to find page with id: " + pageId);
    }

    WaUiMetadata updatedTab =
        page.updateTab(asCommand(user, tab, request), tabId -> findTab(tabId, pageId));

    entityManager.flush();

    return new Tab(
        updatedTab.getId(), updatedTab.getQuestionLabel(), "T".equals(updatedTab.getDisplayInd()));
  }

  WaUiMetadata findTab(long tabId, long pageId) {
    WaUiMetadata metadata = entityManager.find(WaUiMetadata.class, tabId);
    if (metadata == null
        || metadata.getWaTemplateUid().getId() != pageId
        || metadata.getNbsUiComponentUid() != 1010l) {
      throw new PageContentModificationException("Failed to find tab");
    }
    return metadata;
  }

  private PageContentCommand.UpdateTab asCommand(long user, long tab, UpdateTabRequest request) {
    return new PageContentCommand.UpdateTab(
        request.name(), request.visible(), tab, user, Instant.now());
  }
}
