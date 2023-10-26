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
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddStaticElementDefault;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddReadOnlyComments;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.AddStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticHyperLinkRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticElementDefaultRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticReadOnlyCommentsRequest;

@Component
@Transactional
public class PageStaticCreator {

    private final WaUiMetadataRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    // NBS Component ID for the static elements
    private static final Long LINE_SEPARATOR_ID = 1012L;
    private static final Long HYPERLINK_ID = 1003L;
    private static final Long READ_ONLY_COMMENTS_ID = 1014L;
    private static final Long READ_ONLY_PARTICIPANTS_LIST_ID = 1030L;
    private static final Long ORIGINAL_ELECTRONIC_DOCUMENT_LIST_ID = 1036L;

    public PageStaticCreator(
            final WaUiMetadataRepository uiMetadatumRepository,
            final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.uiMetadatumRepository = uiMetadatumRepository;
    }

    public Long addLineSeparator(Long pageId, AddStaticElementDefaultRequest request, Long user) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);

        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddStaticElementDefault(template, orderNum, user, LINE_SEPARATOR_ID, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddStaticElementDefault asAddStaticElementDefault(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            long componentId,
            String adminComments) {
        return new AddStaticElementDefault(page, orderNumber, userId, adminComments, componentId, Instant.now());
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
                request.linkUrl(),
                HYPERLINK_ID));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddHyperLink asAddHyperLink(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String adminComments,
            String label,
            String linkUrl,
            Long componentId) {
        return new AddHyperLink(page, orderNumber, userId, adminComments, label, linkUrl, componentId, Instant.now());
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
                request.adminComments(),
                READ_ONLY_COMMENTS_ID));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private PageContentCommand.AddReadOnlyComments asAddReadOnlyComments(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String comments,
            String adminComments,
            Long componentId) {
        return new AddReadOnlyComments(page, orderNumber, userId, comments, adminComments, componentId, Instant.now());
    }

    public Long addReadOnlyParticipantsList(Long pageId, AddStaticElementDefaultRequest request, Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);

 
        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddStaticElementDefault(template, orderNum, userId, READ_ONLY_PARTICIPANTS_LIST_ID, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    public Long addOriginalElectronicDocList(Long pageId, AddStaticElementDefaultRequest request, Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);

 
        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddStaticElementDefault(template, orderNum, userId, ORIGINAL_ELECTRONIC_DOCUMENT_LIST_ID, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

}
