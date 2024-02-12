package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UnGroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.question.QuestionManagementUtil;
import org.springframework.stereotype.Component;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Instant;

@Component
@Transactional
public class SubSectionGrouper {

  private final EntityManager entityManager;
  private final QuestionManagementUtil questionManagementUtil;

  public SubSectionGrouper(final EntityManager entityManager, QuestionManagementUtil questionManagementUtil
  ) {
    this.entityManager = entityManager;
    this.questionManagementUtil = questionManagementUtil;
  }


  public void group(Long pageId, GroupSubSectionRequest request, Long userId) {
    if (request.blockName() == null) {
      throw new UpdateSubSectionException("SubSection Block Name is required");
    }
    int totalPercentage = 0;
    for (GroupSubSectionRequest.Batch b : request.batches()) {
      if (b.batchTableColumnWidth() == 0) {
        throw new UpdateSubSectionException("batch TableColumnWidth is required");
      }
      totalPercentage += b.batchTableColumnWidth();
    }
    if (totalPercentage != 100) {
      throw new UpdateSubSectionException("the total of batch TableColumnWidth must calculate to 100");
    }
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new PageNotFoundException(pageId);
    }
    page.groupSubSection(asCommand(userId, request), questionManagementUtil.getQuestionNbsUiComponentUids());
    entityManager.flush();
  }

  public void unGroup(Long pageId, UnGroupSubSectionRequest request, Long userId) {
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new PageNotFoundException(pageId);
    }
    page.unGroupSubSection(asCommand(userId, request), questionManagementUtil.getQuestionNbsUiComponentUids());
    entityManager.flush();
  }

  private PageContentCommand.GroupSubsection asCommand(
      Long userId,
      GroupSubSectionRequest request) {
    return new PageContentCommand.GroupSubsection(
        request.id(),
        request.blockName(),
        request.batches(),
        userId,
        Instant.now());
  }

  private PageContentCommand.UnGroupSubsection asCommand(
      Long userId,
      UnGroupSubSectionRequest request) {
    return new PageContentCommand.UnGroupSubsection(
        request.id(),
        request.batches(),
        userId,
        Instant.now());
  }

}
