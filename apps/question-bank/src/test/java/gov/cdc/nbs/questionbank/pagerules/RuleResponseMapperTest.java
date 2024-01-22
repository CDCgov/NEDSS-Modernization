package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleResponseMapperTest {


  @InjectMocks
  private RuleResponseMapper ruleResponseMapper;

  @Mock
  WaQuestionRepository waQuestionRepository;


  @Test
  void should_return_viewRuleResponse() {
    WaRuleMetadata ruleMetadata = getWaRuleMetadata();
    List<Object[]> targetQuestions = getTargetQuestions();
    when(waQuestionRepository.findLabelsByIdentifiers(any())).thenReturn(targetQuestions);
    ViewRuleResponse result = ruleResponseMapper.mapRuleResponse(ruleMetadata);
    assertEquals(ruleMetadata.getId(), result.ruleId());
    assertEquals(ruleMetadata.getWaTemplateUid(), result.templateUid());
    assertEquals(ruleMetadata.getRuleDescText(), result.ruleDescription());
  }

  @Test
  void should_return_page_of_viewRuleResponse() {
    Page<WaRuleMetadata> waRuleMetadataPage = getPageOfWaRuleMetadata();
    List<Object[]> targetQuestions = getTargetQuestions();
    when(waQuestionRepository.findLabelsByIdentifiers(any())).thenReturn(targetQuestions);
    Page<ViewRuleResponse> result = ruleResponseMapper.mapRuleResponse(waRuleMetadataPage);
    assertNotNull(result);
    assertEquals(1, result.toList().size());

  }


  private WaRuleMetadata getWaRuleMetadata() {
    WaRuleMetadata ruleMetadata = new WaRuleMetadata();
    ruleMetadata.setId(1L);
    ruleMetadata.setWaTemplateUid(123l);
    ruleMetadata.setRuleDescText("ruleDescription");
    ruleMetadata.setSourceQuestionIdentifier("sourceQuestionIdentifier");
    ruleMetadata.setSourceValues("value1,value2");
    ruleMetadata.setTargetType("targetType");
    ruleMetadata.setErrormsgText("errorMessage");
    ruleMetadata.setTargetQuestionIdentifier("targetId1,targetId2");
    return ruleMetadata;
  }

  private Page<WaRuleMetadata> getPageOfWaRuleMetadata() {
    List<WaRuleMetadata> ruleMetadataList = Arrays.asList(getWaRuleMetadata());
    Pageable pageable = PageRequest.of(0, 1);
    return new PageImpl<>(ruleMetadataList, pageable, 5);
  }

  private List<Object[]> getTargetQuestions() {
    return Arrays.asList(new Object[] {"label1", "targetId1"}, new Object[] {"label2", "targetId2"});
  }


}
