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

        private final PageRuleHelper pageRuleJSHelper;

        public PageRuleCreator(
                        WaRuleMetaDataRepository waRuleMetaDataRepository,
                        PageRuleHelper pageRuleJSHelper) {
                this.waRuleMetaDataRepository = waRuleMetaDataRepository;
                this.pageRuleJSHelper = pageRuleJSHelper;
        }

        public CreateRuleResponse createPageRule(Long userId, CreateRuleRequest request, Long page) {
                WaRuleMetadata waRuleMetadata = setRuleDataValues(userId, request, page);
                WaRuleMetadata ruleMetadata = new WaRuleMetadata();
                RuleData ruleData = pageRuleJSHelper.createRuleData(request, ruleMetadata);
                ruleMetadata.addPageRule(asAddPageRule(ruleData, request, userId, page));

                waRuleMetaDataRepository.save(waRuleMetadata);
                return new CreateRuleResponse(waRuleMetadata.getId(), "Rule Created Successfully");

        }

        private PageRuleCommand.AddPageRule asAddPageRule(
                        RuleData ruleData,
                        CreateRuleRequest request,
                        long userId,
                        long page) {
                return new PageRuleCommand.AddPageRule(ruleData, request, Instant.now(), Instant.now(), Instant.now(), userId, page);
        }

        private WaRuleMetadata setRuleDataValues(Long userId, CreateRuleRequest request, Long page) {
                WaRuleMetadata ruleMetadata = new WaRuleMetadata();
                RuleData ruleData = pageRuleJSHelper.createRuleData(request, ruleMetadata);
                ruleMetadata.setRuleCd(request.ruleFunction());
                ruleMetadata.setRuleDescText(request.ruleDescription());
                ruleMetadata.setSourceValues(ruleData.sourceValues());
                ruleMetadata.setLogic(request.comparator());
                ruleMetadata.setSourceQuestionIdentifier(ruleData.sourceIdentifier());
                ruleMetadata.setTargetQuestionIdentifier(ruleData.targetIdentifiers());
                ruleMetadata.setTargetType(request.targetType());
                ruleMetadata.setAddTime(Instant.now());
                ruleMetadata.setAddUserId(userId);
                ruleMetadata.setLastChgTime(Instant.now());
                ruleMetadata.setRecordStatusCd("ACTIVE");
                ruleMetadata.setLastChgUserId(userId);
                ruleMetadata.setRecordStatusTime(Instant.now());
                ruleMetadata.setErrormsgText(ruleData.errorMsgText());
                ruleMetadata.setJsFunction(ruleData.jsFunctionNameHelper().jsFunction());
                ruleMetadata.setJsFunctionName(ruleData.jsFunctionNameHelper().jsFunctionName());
                ruleMetadata.setWaTemplateUid(page);
                ruleMetadata.setRuleExpression(ruleData.ruleExpression());

                return ruleMetadata;
        }



}
