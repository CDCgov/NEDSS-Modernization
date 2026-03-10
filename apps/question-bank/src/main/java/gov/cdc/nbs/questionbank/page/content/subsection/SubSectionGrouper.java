package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SubSectionGrouper {

  private final EntityManager entityManager;
  private final SubSectionValidator subSectionValidator;

  public SubSectionGrouper(
      final EntityManager entityManager, final SubSectionValidator subSectionValidator) {
    this.entityManager = entityManager;
    this.subSectionValidator = subSectionValidator;
  }

  public void group(Long pageId, Long subsection, GroupSubSectionRequest request, Long userId) {
    subSectionValidator.validateIfCanBeGrouped(pageId, subsection);
    validate(request);
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    page.groupSubSection(asCommand(subsection, request, userId));
  }

  public void unGroup(Long pageId, long subsectionId, Long userId) {
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    page.unGroupSubSection(asCommand(userId, subsectionId));
  }

  private void validate(GroupSubSectionRequest request) {
    if (request.blockName() == null || !request.blockName().matches("^[\\w]*$")) {
      throw new UpdateSubSectionException(
          "SubSection Block Name is required and only allows alphanumeric or underscore");
    }
    if (request.repeatingNbr() > 5 || request.repeatingNbr() < 0) {
      throw new UpdateSubSectionException("Valid repeat Number values include 0-5");
    }
    int totalPercentage = 0;
    for (GroupSubSectionRequest.Batch b : request.batches()) {
      if (b.appearsInTable()) {
        if (b.label() == null || b.label().trim().equals("")) {
          throw new UpdateSubSectionException("Label in table is required");
        }
        if (b.width() == 0) {
          throw new UpdateSubSectionException("Batch TableColumnWidth is required");
        }
        totalPercentage += b.width();
      }
    }
    if (totalPercentage != 100) {
      throw new UpdateSubSectionException(
          "The total of batch TableColumnWidth must calculate to 100");
    }
  }

  private PageContentCommand.GroupSubsection asCommand(
      long subsection, GroupSubSectionRequest request, long userId) {
    return new PageContentCommand.GroupSubsection(
        subsection,
        request.blockName(),
        request.batches(),
        request.repeatingNbr(),
        userId,
        Instant.now());
  }

  private PageContentCommand.UnGroupSubsection asCommand(Long userId, long subsectionId) {
    return new PageContentCommand.UnGroupSubsection(subsectionId, userId, Instant.now());
  }
}
