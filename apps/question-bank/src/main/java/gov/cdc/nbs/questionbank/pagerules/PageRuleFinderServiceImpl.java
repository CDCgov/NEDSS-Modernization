package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PageRuleFinderServiceImpl implements PageRuleFinderService {

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
        if (ruleMetadata.getSourceValues() != null && ruleMetadata.getTargetQuestionIdentifier() != null) {
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
    public Page<ViewRuleResponse> getAllPageRule(Pageable pageRequest,Long page) {
        Page<WaRuleMetadata> ruleMetadataPage =waRuleMetaDataRepository.findByWaTemplateUid(page,pageRequest);
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
