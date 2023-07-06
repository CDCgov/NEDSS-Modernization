package gov.cdc.nbs.questionbank.page;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
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


        page.setTemplateNm(request.name());
        page.setNndEntityIdentifier(request.messageMappingGuide());
        page.setDescTxt(request.description());

        // If the page is just an initial draft allow update of conditions and Data mart name
        boolean isInitialDraft = page.getTemplateType().equals("Draft") && page.getPublishVersionNbr() == null;
        if (isInitialDraft) {
            page.setDatamartNm(request.dataMartName());

            // Remove any conditions not in the conditions list
            page.getConditionMappings().removeIf(cm -> !request.conditionIds().contains(cm.getConditionCd()));
        }

        // add any conditions not currently mapped
        var existingConditions = page.getConditionMappings().stream().map(cm -> cm.getConditionCd()).toList();
        var conditionsToAdd = request.conditionIds().stream().filter(c -> !existingConditions.contains(c)).toList();
        conditionsToAdd.forEach(c -> {
            page.getConditionMappings().add(toConditionMapping(c, page));
        });

        return pageRepository.save(page);
    }

    private PageCondMapping toConditionMapping(String conditionCode, WaTemplate page) {
        Instant now = Instant.now();
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return new PageCondMapping(
                null,
                page,
                conditionCode,
                now,
                userId,
                now,
                userId);
    }

}
