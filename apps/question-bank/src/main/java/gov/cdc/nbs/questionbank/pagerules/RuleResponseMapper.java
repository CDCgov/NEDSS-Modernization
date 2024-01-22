package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RuleResponseMapper {

  private final WaQuestionRepository waQuestionRepository;

  public RuleResponseMapper(WaQuestionRepository waQuestionRepository) {
    this.waQuestionRepository = waQuestionRepository;
  }

  public ViewRuleResponse mapRuleResponse(WaRuleMetadata ruleMetadata) {
    List<String> sourceValues = new ArrayList<>();
    List<QuestionInfo> targetQuestions = new ArrayList<>();
    if (ruleMetadata.getSourceValues() != null && ruleMetadata.getTargetQuestionIdentifier() != null) {
      String[] sourceValue = ruleMetadata.getSourceValues().split(",");
      sourceValues = Arrays.asList(sourceValue);
      targetQuestions = findLabelsByIdentifiers(Arrays.asList(ruleMetadata.getTargetQuestionIdentifier().split(",")));
    }
    return new ViewRuleResponse(ruleMetadata.getId(), ruleMetadata.getWaTemplateUid(), ruleMetadata.getRuleCd(),
        ruleMetadata.getRuleDescText(), ruleMetadata.getSourceQuestionIdentifier(), sourceValues,
        ruleMetadata.getLogic(), ruleMetadata.getTargetType(), ruleMetadata.getErrormsgText(), targetQuestions);
  }


  public Page<ViewRuleResponse> mapRuleResponse(Page<WaRuleMetadata> ruleMetadataPage) {
    List<ViewRuleResponse> ruleMetadata =
        ruleMetadataPage.getContent().stream()
            .map(rule -> new ViewRuleResponse(rule.getId(), rule.getWaTemplateUid(), rule.getRuleCd(),
                rule.getRuleDescText(), rule.getSourceQuestionIdentifier(),
                buildSourceTargetValues(rule, true),
                rule.getLogic(), rule.getTargetType(), rule.getErrormsgText(),
                findLabelsByIdentifiers(buildSourceTargetValues(rule, false))))
            .toList();
    return new PageImpl<>(ruleMetadata, ruleMetadataPage.getPageable(), ruleMetadataPage.getTotalElements());
  }

  private List<String> buildSourceTargetValues(WaRuleMetadata ruleMetadata, boolean isSource) {

    List<String> sourceValues = new ArrayList<>();
    List<String> targetValues = new ArrayList<>();
    if (isSource) {
      if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
        return sourceValues;
      } else {
        return Arrays.asList(ruleMetadata.getSourceValues().split(","));
      }
    } else {
      if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
        return targetValues;
      } else {
        return Arrays.stream(ruleMetadata.getTargetQuestionIdentifier().split(",")).toList();
      }
    }
  }

  private List<QuestionInfo> findLabelsByIdentifiers(List<String> targetValue) {
    return waQuestionRepository.findLabelsByIdentifiers(targetValue).stream().map(
        question -> new QuestionInfo(question[0].toString(), question[1].toString())).toList();
  }
}
