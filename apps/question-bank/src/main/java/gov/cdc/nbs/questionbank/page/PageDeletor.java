package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PageDeletor {

  private final EntityManager entityManager;
  private final PageUidFinder pageUidFinder;

  public PageDeletor(final EntityManager entityManager, final PageUidFinder pageUidFinder) {
    this.entityManager = entityManager;
    this.pageUidFinder = pageUidFinder;
  }

  @Transactional
  public PageDeleteResponse deletePageDraft(Long id) {
    WaTemplate page = entityManager.find(WaTemplate.class, id);

    if (page == null) {
      throw new PageNotFoundException();
    }

    if (page.getTemplateType().equals(PageConstants.DRAFT)) {

      Long publishedWithDraft =
          pageUidFinder.findTemplateByType(page.getFormCd(), PageConstants.PUBLISHED_WITH_DRAFT);

      if (publishedWithDraft != null) {
        WaTemplate publishedWithDraftPage =
            entityManager.find(WaTemplate.class, publishedWithDraft);
        publishedWithDraftPage.setTemplateType(PageConstants.PUBLISHED);
      }
      entityManager.remove(page);

    } else {
      throw new PageUpdateException(PageConstants.DELETE_DRAFT_FAIL);
    }
    return new PageDeleteResponse(page.getId(), PageConstants.DRAFT_DELETE_SUCCESS);
  }
}
