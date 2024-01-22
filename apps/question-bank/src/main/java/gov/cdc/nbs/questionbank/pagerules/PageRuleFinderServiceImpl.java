package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PageRuleFinderServiceImpl implements PageRuleFinderService {

  private final WaRuleMetaDataRepository waRuleMetaDataRepository;
  private final RuleResponseMapper ruleResponseMapper;

  public PageRuleFinderServiceImpl(
      WaRuleMetaDataRepository waRuleMetaDataRepository, RuleResponseMapper ruleResponseMapper) {
    this.waRuleMetaDataRepository = waRuleMetaDataRepository;
    this.ruleResponseMapper = ruleResponseMapper;
  }

  @Override
  public ViewRuleResponse getRuleResponse(Long ruleId) {
    WaRuleMetadata ruleMetadata = waRuleMetaDataRepository.getReferenceById(ruleId);
    return ruleResponseMapper.mapRuleResponse(ruleMetadata);
  }

  @Override
  public Page<ViewRuleResponse> getAllPageRule(Pageable pageRequest, Long page) {
    Page<WaRuleMetadata> ruleMetadataPage = waRuleMetaDataRepository.findByWaTemplateUid(page, pageRequest);
    return ruleResponseMapper.mapRuleResponse(ruleMetadataPage);
  }

  @Override
  public Page<ViewRuleResponse> findPageRule(SearchPageRuleRequest request, Pageable pageable) {
    Page<WaRuleMetadata> ruleMetadataPage = waRuleMetaDataRepository
        .findAllBySourceValuesContainingIgnoreCaseOrTargetQuestionIdentifierContainingIgnoreCase(
            request.searchValue(), request.searchValue(), pageable);
    return ruleResponseMapper.mapRuleResponse(ruleMetadataPage);
  }


}
