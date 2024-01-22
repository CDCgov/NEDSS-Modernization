package gov.cdc.nbs.questionbank.pagerules;

import java.time.Instant;

import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.command.PageRuleCommand;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;

@Component
@Transactional
public class PageRuleCreator {

  private final WaRuleMetaDataRepository waRuleMetaDataRepository;

  private final PageRuleHelper pageRuleHelper;

  private final RuleResponseMapper ruleResponseMapper;

  public PageRuleCreator(
      WaRuleMetaDataRepository waRuleMetaDataRepository,
      PageRuleHelper pageRuleHelper,
      RuleResponseMapper ruleResponseMapper) {
    this.waRuleMetaDataRepository = waRuleMetaDataRepository;
    this.pageRuleHelper = pageRuleHelper;
    this.ruleResponseMapper = ruleResponseMapper;
  }

  public ViewRuleResponse createPageRule(Long userId, CreateRuleRequest request, Long page) {
    long availableId = waRuleMetaDataRepository.findNextAvailableID();

    RuleData ruleData = pageRuleHelper.createRuleData(request, availableId);
    WaRuleMetadata ruleMetadata = new WaRuleMetadata(asAddPageRule(ruleData, request, userId, page));

    waRuleMetaDataRepository.save(ruleMetadata);
    return ruleResponseMapper.mapRuleResponse(ruleMetadata);
  }

  private PageRuleCommand.AddPageRule asAddPageRule(
      RuleData ruleData,
      CreateRuleRequest request,
      long userId,
      long page) {
    return new PageRuleCommand.AddPageRule(ruleData, request, Instant.now(), userId, page);
  }
}
