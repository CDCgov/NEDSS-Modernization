package gov.cdc.nbs.questionbank.pagerules;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.command.PageRuleCommand;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;

@Component
@Transactional
public class PageRuleCreator {

        private final WaRuleMetaDataRepository waRuleMetaDataRepository;

        private final PageRuleHelper pageRuleHelper;

        public PageRuleCreator(
                        WaRuleMetaDataRepository waRuleMetaDataRepository,
                        PageRuleHelper pageRuleHelper) {
                this.waRuleMetaDataRepository = waRuleMetaDataRepository;
                this.pageRuleHelper = pageRuleHelper;
        }

        public CreateRuleResponse createPageRule(Long userId, CreateRuleRequest request, Long page) {
                long availableId = waRuleMetaDataRepository.findNextAvailableID();

                RuleData ruleData = pageRuleHelper.createRuleData(request, availableId);
                WaRuleMetadata ruleMetadata = new WaRuleMetadata(asAddPageRule(ruleData, request, userId, page));
                
                waRuleMetaDataRepository.save(ruleMetadata);
                return new CreateRuleResponse(ruleMetadata.getId(), "Rule Created Successfully");
        }

        private PageRuleCommand.AddPageRule asAddPageRule(
                        RuleData ruleData,
                        CreateRuleRequest request,
                        long userId,
                        long page) {
                return new PageRuleCommand.AddPageRule(ruleData, request, Instant.now(), Instant.now(), Instant.now(), userId, page);
        }
}
