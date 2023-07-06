package gov.cdc.nbs.questionbank.page;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.request.UpdatePageDetailsRequest;

@Component
public class PageUpdater {

    private final WaTemplateRepository pageRepository;

    public PageUpdater(WaTemplateRepository pagRepository) {
        this.pageRepository = pagRepository;
    }

    public void update(Long pageId, UpdatePageDetailsRequest request) {
        WaTemplate page = pageRepository.findById(pageId)
                .orElseThrow(() -> new PageNotFoundException("Failed to find page with id: " + pageId));


        // If the page is just a draft allow update of conditions and Data mart name

        // else only allow adding of conditions

        // TODO update history?
    }

}
