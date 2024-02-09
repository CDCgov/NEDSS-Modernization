package gov.cdc.nbs.questionbank.pagerules;


import gov.cdc.nbs.questionbank.pagerules.response.RuleResponse;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PageRuleReader {


  private final PageRuleFinder pageRuleFinder;
  private final WaQuestionRepository waQuestionRepository;

  public PageRuleReader(final PageRuleFinder pageRuleFinder,
      final WaQuestionRepository waQuestionRepository) {
    this.pageRuleFinder = pageRuleFinder;
    this.waQuestionRepository = waQuestionRepository;
  }

  public Rule findByRuleId(Long ruleId) {
    RuleResponse ruleResponse = pageRuleFinder.findByRuleId(ruleId);
    if (ruleResponse != null)
      return mapRuleResponse(ruleResponse);
    return null;
  }

  public Page<Rule> findByPageId(Long pageId, final Pageable pageable) {
    Page<RuleResponse> ruleResponsePage = pageRuleFinder.findByPageId(pageId, pageable);
    List<Rule> ruleList = ruleResponsePage.stream().map(this::mapRuleResponse).toList();
    return new PageImpl<>(ruleList, pageable, ruleResponsePage.getTotalElements());
  }

  public Page<Rule> searchPageRule(SearchPageRuleRequest request, final Pageable pageable) {
    Page<RuleResponse> ruleResponsePage = pageRuleFinder.searchPageRule(request.searchValue(), pageable);
    List<Rule> ruleList = ruleResponsePage.stream().map(this::mapRuleResponse).toList();
    return new PageImpl<>(ruleList, pageable, ruleResponsePage.getTotalElements());
  }

  private Rule mapRuleResponse(RuleResponse ruleResponse) {
    QuestionInfo sourceQuestion = findLabelByIdentifier(ruleResponse.sourceQuestion());
    boolean anySource = ruleResponse.ruleExpression().contains("( )");
    List<String> sourceValues = null;
    if (ruleResponse.sourceValues() != null)
      sourceValues = Arrays.asList(ruleResponse.sourceValues().split(","));
    List<QuestionInfo> targets = findLabelsByIdentifiers(Arrays.asList(ruleResponse.targetQuestions().split(",")));
    return new Rule(
        ruleResponse.ruleId(),
        ruleResponse.templateId(),
        ruleResponse.function(),
        ruleResponse.description(),
        sourceQuestion,
        anySource,
        sourceValues,
        ruleResponse.comparator(),
        ruleResponse.targetType(),
        targets);
  }

  private List<QuestionInfo> findLabelsByIdentifiers(List<String> targetValue) {
    return waQuestionRepository.findLabelsByIdentifiers(targetValue).stream().map(
        question -> new QuestionInfo(question[0].toString(), question[1].toString())).toList();
  }

  private QuestionInfo findLabelByIdentifier(String identifier) {
    Optional<QuestionInfo> questionInfoOptional =
        waQuestionRepository.findLabelByIdentifier(identifier).stream().findFirst().map(
            question -> new QuestionInfo(question[0].toString(), question[1].toString()));
    return questionInfoOptional.isPresent() ? questionInfoOptional.get() : null;
  }

}
