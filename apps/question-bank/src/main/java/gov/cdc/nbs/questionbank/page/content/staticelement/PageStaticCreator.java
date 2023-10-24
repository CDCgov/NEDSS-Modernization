package gov.cdc.nbs.questionbank.page.content.staticelement;


import java.time.Instant;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.questionbank.entity.NbsUiComponent;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddLineSeparator;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.AddStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticLineSeparatorRequest;

@Component
@Transactional
public class PageStaticCreator {
    
    private final WaUiMetadataRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    public static final Long LINE_SEPARATOR_ID = (long) 1012;
    public static final Long HYPERLINK_ID = (long) 1003;
    public static final Long READ_ONLY_ID = (long) 1030;


    public PageStaticCreator(
        final WaUiMetadataRepository uiMetadatumRepository,
        final EntityManager entityManager) {
            this.entityManager = entityManager;
            this.uiMetadatumRepository = uiMetadatumRepository;
    }

    public Long addLineSeparator(Long pageId, AddStaticLineSeparatorRequest request, Long user) {
        if(pageId == null || request.orderNum() == null) {
            throw new AddStaticElementException("Page and Order Number are required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);
 
        // i know my component id is 1012 for line separator, this is never going to change

        // Find max order number for the page
        Integer currentMaxOrder = uiMetadatumRepository.findMaxOrderNbrForPage(pageId);
        Integer orderNbr = request.orderNum() > currentMaxOrder + 1 ? currentMaxOrder + 1 : request.orderNum();

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, request.orderNum());

        WaUiMetadata staticElementEntry = new WaUiMetadata(asAdd(template, LINE_SEPARATOR_ID, orderNbr, user, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddLineSeparator asAdd(
            WaTemplate page,
            Long componentId,
            Integer orderNumber,
            long userId,
            String adminComments) {
        return new AddLineSeparator(page, componentId, orderNumber, userId, adminComments, Instant.now());
    }

}
