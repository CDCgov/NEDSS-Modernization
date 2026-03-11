package gov.cdc.nbs.questionbank.page.content.staticelement;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.AddStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageStaticCreator {

  private final WaUiMetadataRepository uiMetadatumRepository;
  private final EntityManager entityManager;
  private final NbsConfigurationRepository configRepository;
  private final IdGeneratorService idGenerator;

  private static final String PAGE_REQUIRED_EXCEPTION_MESSAGE = "Page is required";
  private static final String PAGE_NOT_FOUND_EXCEPTION_MESSAGE = "Unable to find page";
  private static final String SUBSECTION_NOT_FOUND_EXCEPTION_MESSAGE = "Failed to find subsection";
  private static final String PAGE_IF_DRAFT = "Draft";

  // NBS Component ID for the static elements

  public PageStaticCreator(
      final WaUiMetadataRepository uiMetadatumRepository,
      final EntityManager entityManager,
      final NbsConfigurationRepository configRepository,
      final IdGeneratorService idGenerator) {
    this.entityManager = entityManager;
    this.uiMetadatumRepository = uiMetadatumRepository;
    this.configRepository = configRepository;
    this.idGenerator = idGenerator;
  }

  public Long addLineSeparator(Long pageId, StaticContentRequests.AddDefault request, Long user) {
    if (pageId == null) {
      throw new AddStaticElementException(PAGE_REQUIRED_EXCEPTION_MESSAGE);
    }

    WaTemplate template = entityManager.find(WaTemplate.class, pageId);

    if (!template.getTemplateType().equals(PAGE_IF_DRAFT)) {
      throw new AddStaticElementException(PAGE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    WaUiMetadata subSection =
        uiMetadatumRepository
            .findById(request.subSectionId())
            .orElseThrow(
                () -> new AddStaticElementException(SUBSECTION_NOT_FOUND_EXCEPTION_MESSAGE));

    Integer orderNum =
        uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

    uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

    WaUiMetadata staticElementEntry =
        new WaUiMetadata(asAddLineSeparator(template, orderNum, user, request.adminComments()));
    staticElementEntry.setQuestionIdentifier(getLocalId());

    return uiMetadatumRepository.save(staticElementEntry).getId();
  }

  private PageContentCommand.AddLineSeparator asAddLineSeparator(
      WaTemplate page, Integer orderNumber, long userId, String adminComments) {
    return new PageContentCommand.AddLineSeparator(
        page, orderNumber, userId, adminComments, Instant.now());
  }

  public Long addHyperLink(Long pageId, StaticContentRequests.AddHyperlink request, Long userId) {
    if (pageId == null) {
      throw new AddStaticElementException(PAGE_REQUIRED_EXCEPTION_MESSAGE);
    }
    if (request.label() == null || request.linkUrl() == null) {
      throw new AddStaticElementException("Label and Link URL are required");
    }

    WaTemplate template = entityManager.find(WaTemplate.class, pageId);

    if (!template.getTemplateType().equals(PAGE_IF_DRAFT)) {
      throw new AddStaticElementException(PAGE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    // Find max order number for the subsection
    WaUiMetadata subSection =
        uiMetadatumRepository
            .findById(request.subSectionId())
            .orElseThrow(
                () -> new AddStaticElementException(SUBSECTION_NOT_FOUND_EXCEPTION_MESSAGE));

    Integer orderNum =
        uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

    uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

    WaUiMetadata staticElementEntry =
        new WaUiMetadata(
            asAddHyperLink(
                template,
                orderNum,
                userId,
                request.adminComments(),
                request.label(),
                request.linkUrl()));

    staticElementEntry.setQuestionIdentifier(getLocalId());

    return uiMetadatumRepository.save(staticElementEntry).getId();
  }

  private PageContentCommand.AddHyperLink asAddHyperLink(
      WaTemplate page,
      Integer orderNumber,
      long userId,
      String adminComments,
      String label,
      String linkUrl) {
    return new PageContentCommand.AddHyperLink(
        page, orderNumber, userId, adminComments, label, linkUrl, Instant.now());
  }

  public Long addReadOnlyComments(
      Long pageId, StaticContentRequests.AddReadOnlyComments request, Long userId) {
    if (pageId == null) {
      throw new AddStaticElementException(PAGE_REQUIRED_EXCEPTION_MESSAGE);
    }
    if (request.commentsText() == null) {
      throw new AddStaticElementException("Comments are required");
    }

    WaTemplate template = entityManager.find(WaTemplate.class, pageId);

    if (!template.getTemplateType().equals(PAGE_IF_DRAFT)) {
      throw new AddStaticElementException(PAGE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    // Find max order number for the subsection
    WaUiMetadata subSection =
        uiMetadatumRepository
            .findById(request.subSectionId())
            .orElseThrow(
                () -> new AddStaticElementException(SUBSECTION_NOT_FOUND_EXCEPTION_MESSAGE));

    Integer orderNum =
        uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

    uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

    WaUiMetadata staticElementEntry =
        new WaUiMetadata(
            asAddReadOnlyComments(
                template, orderNum, userId, request.commentsText(), request.adminComments()));

    staticElementEntry.setQuestionIdentifier(getLocalId());

    return uiMetadatumRepository.save(staticElementEntry).getId();
  }

  private PageContentCommand.AddReadOnlyComments asAddReadOnlyComments(
      WaTemplate page, Integer orderNumber, long userId, String comments, String adminComments) {
    return new PageContentCommand.AddReadOnlyComments(
        page, orderNumber, userId, comments, adminComments, Instant.now());
  }

  public Long addReadOnlyParticipantsList(
      Long pageId, StaticContentRequests.AddDefault request, Long userId) {
    if (pageId == null) {
      throw new AddStaticElementException(PAGE_REQUIRED_EXCEPTION_MESSAGE);
    }

    WaTemplate template = entityManager.find(WaTemplate.class, pageId);

    if (!template.getTemplateType().equals(PAGE_IF_DRAFT)) {
      throw new AddStaticElementException(PAGE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    WaUiMetadata subSection =
        uiMetadatumRepository
            .findById(request.subSectionId())
            .orElseThrow(
                () -> new AddStaticElementException(SUBSECTION_NOT_FOUND_EXCEPTION_MESSAGE));

    Integer orderNum =
        uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

    uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

    WaUiMetadata staticElementEntry =
        new WaUiMetadata(
            asAddReadOnlyParticipantsList(template, orderNum, userId, request.adminComments()));

    staticElementEntry.setQuestionIdentifier(getLocalId());

    return uiMetadatumRepository.save(staticElementEntry).getId();
  }

  private PageContentCommand.AddReadOnlyParticipantsList asAddReadOnlyParticipantsList(
      WaTemplate page, Integer orderNumber, long userId, String adminComments) {
    return new PageContentCommand.AddReadOnlyParticipantsList(
        page, orderNumber, userId, adminComments, Instant.now());
  }

  public Long addOriginalElectronicDocList(
      Long pageId, StaticContentRequests.AddDefault request, Long userId) {
    if (pageId == null) {
      throw new AddStaticElementException(PAGE_REQUIRED_EXCEPTION_MESSAGE);
    }

    WaTemplate template = entityManager.find(WaTemplate.class, pageId);

    if (!template.getTemplateType().equals(PAGE_IF_DRAFT)) {
      throw new AddStaticElementException(PAGE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    WaUiMetadata subSection =
        uiMetadatumRepository
            .findById(request.subSectionId())
            .orElseThrow(
                () -> new AddStaticElementException(SUBSECTION_NOT_FOUND_EXCEPTION_MESSAGE));

    Integer orderNum =
        uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subSection.getOrderNbr());

    uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, orderNum);

    WaUiMetadata staticElementEntry =
        new WaUiMetadata(
            asAddOriginalElectronicDocList(template, orderNum, userId, request.adminComments()));

    staticElementEntry.setQuestionIdentifier(getLocalId());

    return uiMetadatumRepository.save(staticElementEntry).getId();
  }

  private PageContentCommand.AddOrignalElectronicDocList asAddOriginalElectronicDocList(
      WaTemplate page, Integer orderNumber, long userId, String adminComments) {
    return new PageContentCommand.AddOrignalElectronicDocList(
        page, orderNumber, userId, adminComments, Instant.now());
  }

  private String getLocalId() {
    String nbsClassCode = getNbsClassCode();
    return nbsClassCode
        + idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS_QUESTION_ID_LDF).getId();
  }

  private String getNbsClassCode() {
    return configRepository
        .findById("NBS_CLASS_CODE")
        .orElseThrow(() -> new CreateQuestionException("Failed to lookup NBS_CLASS_CODE"))
        .getConfigValue();
  }
}
