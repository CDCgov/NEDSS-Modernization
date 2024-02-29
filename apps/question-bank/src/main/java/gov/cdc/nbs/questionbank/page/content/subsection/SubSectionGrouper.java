package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;


@Transactional
@Component
public class SubSectionGrouper {

  private final EntityManager entityManager;
  private final SubSectionValidator subSectionValidator;

  public SubSectionGrouper(final EntityManager entityManager,
      final SubSectionValidator subSectionValidator) {
    this.entityManager = entityManager;
    this.subSectionValidator = subSectionValidator;
  }

  public void group(Long pageId, GroupSubSectionRequest request, Long userId) {
    subSectionValidator.validateIfCanBeGrouped(pageId, request.id());
    validateEntries(request);
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    page.groupSubSection(asCommand(userId, request));
  }

  public void unGroup(Long pageId, long subsectionId, Long userId) {
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    List<Long> batches =
        subSectionValidator.getSubsectionElements(pageId, subsectionId).stream().map(WaUiMetadata::getId).toList();
    page.unGroupSubSection(asCommand(userId, subsectionId, batches));
  }

  private void validateEntries(GroupSubSectionRequest request) {
    if (request.blockName() == null || request.blockName().trim().isEmpty()) {
      throw new UpdateSubSectionException("SubSection Block Name is required");
    }
    if (request.repeatingNbr() > 5) {
      throw new UpdateSubSectionException("Valid repeat Number values include 0-5");
    }
    int totalPercentage = 0;
    for (GroupSubSectionRequest.Batch b : request.batches()) {
      if (b.batchTableColumnWidth() == 0) {
        throw new UpdateSubSectionException("Batch TableColumnWidth is required");
      }
      totalPercentage += b.batchTableColumnWidth();
    }
    if (totalPercentage != 100) {
      throw new UpdateSubSectionException("The total of batch TableColumnWidth must calculate to 100");
    }
  }

  private PageContentCommand.GroupSubsection asCommand(
      Long userId,
      GroupSubSectionRequest request) {
    return new PageContentCommand.GroupSubsection(
        request.id(),
        request.blockName(),
        request.batches(),
        request.repeatingNbr(),
        userId,
        Instant.now());
  }

  private PageContentCommand.UnGroupSubsection asCommand(
      Long userId,
      long subsectionId,
      List<Long> batches) {
    return new PageContentCommand.UnGroupSubsection(
        subsectionId,
        batches,
        userId,
        Instant.now());
  }
}
