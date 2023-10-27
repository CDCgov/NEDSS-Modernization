package gov.cdc.nbs.questionbank.page.content.staticelement;

import java.time.Instant;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.*;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.AddStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.PageStaticRequests.*;

@Component
@Transactional
public class PageStaticCreator {

    private final WaUiMetadataRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    // NBS Component ID for the static elements

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

        WaTemplate template = entityManager.find(WaTemplate.class, pageId);

        if (!template.getTemplateType().equals("Draft")) {
            throw new AddStaticElementException("Unable to find page");
        }

        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddLineSeparator(template, orderNum, user, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private AddLineSeparator asAddLineSeparator(
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

        WaTemplate template = entityManager.find(WaTemplate.class, pageId);

        if (!template.getTemplateType().equals("Draft")) {
            throw new AddStaticElementException("Unable to find page");
        }

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

    private AddHyperLink asAddHyperLink(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String adminComments,
            String label,
            String linkUrl) {
        return new AddHyperLink(page, orderNumber, userId, adminComments, label, linkUrl, Instant.now());
    }

    public Long addReadOnlyComments(Long pageId, AddStaticReadOnlyCommentsRequest request,
            Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }
        if (request.commentsText() == null) {
            throw new AddStaticElementException("Comments are required");
        }

        WaTemplate template = entityManager.find(WaTemplate.class, pageId);

        if (!template.getTemplateType().equals("Draft")) {
            throw new AddStaticElementException("Unable to find page");
        }

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

    private AddReadOnlyComments asAddReadOnlyComments(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String comments,
            String adminComments) {
        return new AddReadOnlyComments(page, orderNumber, userId, comments, adminComments, Instant.now());
    }

    public Long addReadOnlyParticipantsList(Long pageId, AddStaticElementDefaultRequest request,
            Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.find(WaTemplate.class, pageId);

        if (!template.getTemplateType().equals("Draft")) {
            throw new AddStaticElementException("Unable to find page");
        }

        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddReadOnlyParticipantsList(template, orderNum, userId, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private AddReadOnlyParticipantsList asAddReadOnlyParticipantsList(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String adminComments) {
        return new AddReadOnlyParticipantsList(page, orderNumber, userId, adminComments, Instant.now());
    }

    public Long addOriginalElectronicDocList(Long pageId, AddStaticElementDefaultRequest request,
            Long userId) {
        if (pageId == null) {
            throw new AddStaticElementException("Page is required");
        }

        WaTemplate template = entityManager.find(WaTemplate.class, pageId);

        if (!template.getTemplateType().equals("Draft")) {
            throw new AddStaticElementException("Unable to find page");
        }

        WaUiMetadata subSection = uiMetadatumRepository.findById(request.subSectionId())
                .orElseThrow(() -> new AddStaticElementException("Failed to find subsection"));

        Integer orderNum = uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

        WaUiMetadata staticElementEntry = new WaUiMetadata(
                asAddOriginalElectronicDocList(template, orderNum, userId, request.adminComments()));

        return uiMetadatumRepository.save(staticElementEntry).getId();
    }

    private AddOrignalElectronicDocList asAddOriginalElectronicDocList(
            WaTemplate page,
            Integer orderNumber,
            long userId,
            String adminComments) {
        return new AddOrignalElectronicDocList(page, orderNumber, userId, adminComments, Instant.now());
    }

}
