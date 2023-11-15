package gov.cdc.nbs.questionbank.page;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;


@Service
public class PageDeletor {

    private final EntityManager entityManager;

    private final PageDraftFinder pageDraftFinder;

    public PageDeletor(
            final EntityManager entityManager,
            final PageDraftFinder pageDraftFinder) {
        this.entityManager = entityManager;
        this.pageDraftFinder = pageDraftFinder;
    }


    @Transactional
    public PageDeleteResponse deletePageDraft(Long id) {
        WaTemplate page = entityManager.find(WaTemplate.class, id);

        if (page == null) {
            throw new PageNotFoundException();
        }

        if (page.getTemplateType().equals(PageConstants.DRAFT)) {
            entityManager.remove(page);
        } else if (page.getTemplateType().equals(PageConstants.PUBLISHED_WITH_DRAFT)) {
            Long draftPageId = pageDraftFinder.findDraftTemplate(page);

            WaTemplate draftPage = entityManager.find(WaTemplate.class, draftPageId);

            if (draftPage == null) {
                throw new PageNotFoundException();
            }

            entityManager.remove(draftPage);

            page.setTemplateType(PageConstants.PUBLISHED);
        } else {
            throw new PageUpdateException(PageConstants.DELETE_DRAFT_FAIL);
        }

        return new PageDeleteResponse(page.getId(), PageConstants.DRAFT_DELETE_SUCCESS);
    }

}
