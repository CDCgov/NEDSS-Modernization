package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.RuleCreatedEventProducer;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class PageRuleFinderServiceImpl implements PageRuleFinderService {
    private static final String DATE_COMPARE = "Date Compare";
    private static final String ELSE = " } else { \n";
    private static final String IF = "\n if(";
    private static final String ARRAY = "($j.inArray('";
    private static final String DISABLE = "Disable";
    private static final String ENABLE = "Enable";
    private static final String REQUIRE_IF = "Require If";
    private static final String HIDE = "Hide";
    private static final String ANY_SOURCE_VALUE = "( Any Source Value )";
    private static final String PG_ENABLE_ELEMENT = "pgEnableElement('";
    private static final String PG_DISABLE_ELEMENT = "pgDisableElement('";
    private static final String SELECTED = " :selected').each(function(i, selected){";
    private static final String FUNCTION = "function ";
    private static final String ACTION_1 = "()\n{";

    private final WaRuleMetaDataRepository waRuleMetaDataRepository;


    public PageRuleFinderServiceImpl(
            WaRuleMetaDataRepository waRuleMetaDataRepository) {
        this.waRuleMetaDataRepository = waRuleMetaDataRepository;
    }

    @Override
    public ViewRuleResponse getRuleResponse(Long ruleId) {
        WaRuleMetadata ruleMetadata = waRuleMetaDataRepository.getReferenceById(ruleId);
        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
            sourceValues.add(null);
            targetValues.add(null);
        } else {
            String[] sourceValue = ruleMetadata.getSourceValues().split(",");
            sourceValues = Arrays.asList(sourceValue);
            String[] targetValue = ruleMetadata.getTargetQuestionIdentifier().split(",");
            targetValues = Arrays.asList(targetValue);
        }
        return new ViewRuleResponse(ruleId, ruleMetadata.getWaTemplateUid(), ruleMetadata.getRuleCd(),
                ruleMetadata.getRuleDescText(), ruleMetadata.getSourceQuestionIdentifier(), sourceValues,
                ruleMetadata.getLogic(), ruleMetadata.getTargetType(), ruleMetadata.getErrormsgText(), targetValues);
    }

    @Override
    public Page<ViewRuleResponse> getAllPageRule(Pageable pageRequest) {
        Page<WaRuleMetadata> ruleMetadataPage =waRuleMetaDataRepository.findAll(pageRequest);
        return buildViewRuleResponse(ruleMetadataPage);
    }

    @Override
    public Page<ViewRuleResponse> findPageRule(SearchPageRuleRequest request, Pageable pageable) {
        Page<WaRuleMetadata> ruleMetadataPage =waRuleMetaDataRepository.findAllBySourceValuesContainingIgnoreCaseOrTargetQuestionIdentifierContainingIgnoreCase(request.searchValue(),request.searchValue(),pageable);
        return buildViewRuleResponse(ruleMetadataPage);
    }

    private Page<ViewRuleResponse> buildViewRuleResponse(Page<WaRuleMetadata> ruleMetadataPage){
        List<ViewRuleResponse> ruleMetadata =
                ruleMetadataPage.getContent().stream().map(rule ->new ViewRuleResponse(rule.getId(), rule.getWaTemplateUid(), rule.getRuleCd(),
                        rule.getRuleDescText(), rule.getSourceQuestionIdentifier(), buildSourceTargetValues(rule,true),
                        rule.getLogic(), rule.getTargetType(), rule.getErrormsgText(),
                        buildSourceTargetValues(rule,false))).toList();
        return new PageImpl<>(ruleMetadata,ruleMetadataPage.getPageable(),ruleMetadataPage.getTotalElements());
    }

    private List<String> buildSourceTargetValues(WaRuleMetadata ruleMetadata, boolean isSource) {

        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        if(isSource){
            if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
                return  sourceValues;
            } else {
               return Arrays.asList(ruleMetadata.getSourceValues().split(","));
            }
        }else{
            if (ruleMetadata.getSourceValues() == null || ruleMetadata.getTargetQuestionIdentifier() == null) {
                return targetValues;
            } else {
                return Arrays.stream(ruleMetadata.getTargetQuestionIdentifier().split(",")).toList();
            }

        }

    }
}
