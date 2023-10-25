package gov.cdc.nbs.questionbank.page.content.staticelement;


import java.time.Instant;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddHyperLink;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddLineSeparator;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.AddStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticHyperLink;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticLineSeparatorRequest;

@Component
@Transactional
public class PageStaticCreator {
    
    private final WaUiMetadataRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    public static final Long LINE_SEPARATOR_ID = 1012L;
    public static final Long HYPERLINK_ID = 1003L;
    public static final Long READ_ONLY_ID = 1030L;


    public PageStaticCreator(
        final WaUiMetadataRepository uiMetadatumRepository,
        final EntityManager entityManager) {
            this.entityManager = entityManager;
            this.uiMetadatumRepository = uiMetadatumRepository;
    }

    public Long addLineSeparator(Long pageId, AddStaticLineSeparatorRequest request, Long user) {
        if(pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);
 
        // i know my component id is 1012 for line separator, this is never going to change
        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId()).orElseThrow( () -> new AddStaticElementException("Failed to find subsection"));


        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());



        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(asAddLineSeparator(template, LINE_SEPARATOR_ID, orderNum, user, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddLineSeparator asAddLineSeparator(
            WaTemplate page,
            Long componentId,
            Integer orderNumber,
            long userId,
            String adminComments) {
        return new AddLineSeparator(page, componentId, orderNumber, userId, adminComments, Instant.now());
    }

    public Long addHyperLink(Long pageId, AddStaticHyperLink request, Long userId) {
        if(pageId == null || request.orderNum() == null) {
            throw new AddStaticElementException("Page and Order Number are required");
        }
        if(request.label() == null || request.linkUrl() == null) {
            throw new AddStaticElementException("Label and Link URL are required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);
 
        // i know my component id is 1012 for line separator, this is never going to change

        // Find max order number for the page
        Integer currentMaxOrder = uiMetadatumRepository.findMaxOrderNbrForPage(pageId);
        Integer orderNbr = request.orderNum() > currentMaxOrder + 1 ? currentMaxOrder + 1 : request.orderNum();

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, request.orderNum());

        WaUiMetadata staticElementEntry = new WaUiMetadata(asAddHyperLink(template, LINE_SEPARATOR_ID, orderNbr, userId, request.adminComments(), request.label(), request.linkUrl()));



        return null;
    }

    private PageContentCommand.AddHyperLink asAddHyperLink(
            WaTemplate page,
            Long componentId,
            Integer orderNumber,
            long userId,
            String adminComments,
            String label,
            String linkUrl) {
        return new AddHyperLink(page, componentId, orderNumber, userId, adminComments, label, linkUrl, Instant.now());
    }

}
