package gov.cdc.nbs.questionbank.page.publish;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.classic.ClassicPublishPagePreparer;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPublishPageRequester;
import gov.cdc.nbs.questionbank.page.exception.PagePublishException;
import gov.cdc.nbs.questionbank.page.request.PagePublishRequest;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class PagePublisher {

  private final ClassicPublishPagePreparer publishPagePreparer;

  private final ClassicPublishPageRequester publishPageRequester;

  private final EntityManager entityManager;

  public PagePublisher(
      final ClassicPublishPagePreparer preparer,
      final ClassicPublishPageRequester requester,
      final EntityManager entityManager) {
    this.publishPagePreparer = preparer;
    this.entityManager = entityManager;
    this.publishPageRequester = requester;
  }

  public void publishPage(Long pageId, PagePublishRequest publishRequest) {
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);

    if (!page.getTemplateType().equals(PageConstants.DRAFT)) {
      throw new PagePublishException("can only publish a draft page");
    }

    publishPagePreparer.prepare(page.getId());

    publishPageRequester.request(publishRequest.versionNotes());

    entityManager.clear();

    page = entityManager.find(WaTemplate.class, page.getId());

    if (!page.getTemplateType().equals(PageConstants.PUBLISHED)) {
      throw new PagePublishException("unable to publish page");
    }
  }
}
