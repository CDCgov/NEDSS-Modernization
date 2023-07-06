package gov.cdc.nbs.questionbank.page;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.request.UpdatePageDetailsRequest;

@Component
@Transactional
public class PageUpdater {

    private final WaTemplateRepository pageRepository;
    private final UserDetailsProvider userDetailsProvider;

    public PageUpdater(
            WaTemplateRepository pagRepository,
            UserDetailsProvider userDetailsProvider) {
        this.pageRepository = pagRepository;
        this.userDetailsProvider = userDetailsProvider;
    }

    /**
     * Updates a page (Wa_template) details. No history event is triggered for this update type.
     * 
     * @param pageId
     * @param request
     */
    public WaTemplate update(Long pageId, UpdatePageDetailsRequest request) {
        WaTemplate page = pageRepository.findByIdAndTemplateTypeIn(pageId, List.of("Draft", "Published"))
                .orElseThrow(() -> new PageNotFoundException("Failed to find page with id: " + pageId));

        page.update(asUpdate(request));
        return pageRepository.save(page);
    }

    private PageCommand.UpdateDetails asUpdate(UpdatePageDetailsRequest request) {
        return new PageCommand.UpdateDetails(
                request.name(),
                request.messageMappingGuide(),
                request.dataMartName(),
                request.description(),
                request.conditionIds(),
                userDetailsProvider.getCurrentUserDetails().getId(),
                Instant.now());
    }

}
