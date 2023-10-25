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
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddReadOnlyComments;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.AddStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticHyperLinkRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticLineSeparatorRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticReadOnlyCommentsRequest;

@Component
@Transactional
public class PageStaticCreator {

    private final WaUiMetadataRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    public PageStaticCreator(
            final WaUiMetadataRepository uiMetadatumRepository,
            final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.uiMetadatumRepository = uiMetadatumRepository;
    }

    public Long addLineSeparator(Long pageId, AddStaticLineSeparatorRequest request, Long user) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);

        // i know my component id is 1012 for line separator, this is never going to
        // change
        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddLineSeparator(template, orderNum, user, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddLineSeparator asAddLineSeparator(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String adminComments) {
        return new AddLineSeparator(page, orderNumber, userId, adminComments, Instant.now());
    }

    public Long addHyperLink(Long pageId, AddStaticHyperLinkRequest request, Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }
        if (request.label() == null || request.linkUrl() == null) {
            throw new AddStaticElementException("Label and Link URL are required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);

        // Find max order number for the subsection
        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(asAddHyperLink(
                template,
                orderNum,
                userId,
                request.adminComments(),
                request.label(),
                request.linkUrl()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddHyperLink asAddHyperLink(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String adminComments,
            String label,
            String linkUrl) {
        return new AddHyperLink(page, orderNumber, userId, adminComments, label, linkUrl, Instant.now());
    }

    public Long addReadOnlyComments(Long pageId, AddStaticReadOnlyCommentsRequest request, Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }
        if (request.commentsText() == null) {
            throw new AddStaticElementException("Comments are required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);

        // Find max order number for the subsection
        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(asAddReadOnlyComments(
                template,
                orderNum,
                userId,
                request.commentsText(),
                request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddReadOnlyComments asAddReadOnlyComments(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String comments,
            String adminComments) {
        return new AddReadOnlyComments(page, orderNumber, userId, comments, adminComments, Instant.now());
    }

}
