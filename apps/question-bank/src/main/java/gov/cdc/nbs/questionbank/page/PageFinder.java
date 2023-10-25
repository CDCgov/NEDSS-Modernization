package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.response.PageDetailResponse.PageRule;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageFinder {

  @Autowired
  private WaRuleMetaDataRepository waRuleMetaDataRepository;

  public List<WaUiMetadata> mergeOrderedQuetionLists(List<WaUiMetadata> questions1, List<WaUiMetadata> questions2) {
    if (questions1.isEmpty() && !questions2.isEmpty()) {
      return questions2;
    }
    if (!questions1.isEmpty() && questions2.isEmpty()) {
      return questions1;
    }
    if (questions1.isEmpty() && questions2.isEmpty()) {
      return new ArrayList<>();
    }
    Integer qOneLastItemOrder = questions1.get(questions1.size() - 1).getOrderNbr();
    Integer qTwoLastItemOrder = questions2.get(questions2.size() - 1).getOrderNbr();
    if (qOneLastItemOrder.compareTo(qTwoLastItemOrder) > 0) {
      questions2.addAll(questions1);
      return questions2;
    } else {
      questions1.addAll(questions2);
      return questions1;
    }
  }

  public List<PageRule> getPageRules(Long pageId) {
    List<PageRule> rules = new ArrayList<>();
    List<WaRuleMetadata> rawRules = waRuleMetaDataRepository.findByWaTemplateUid(pageId);
    for (WaRuleMetadata rawRule : rawRules) {
      PageRule rule = new PageRule(rawRule.getId(), pageId, rawRule.getLogic(), rawRule.getSourceValues(),
          rawRule.getRuleCd(), rawRule.getSourceQuestionIdentifier(), rawRule.getTargetQuestionIdentifier());
      rules.add(rule);
    }

    return rules;

  }



}
