package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.response.RuleResponse;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PageRuleReaderTest {



  @Mock
  private WaQuestionRepository waQuestionRepository;

  @Mock
  PageRuleFinder pageRuleFinder;

  @InjectMocks
  private PageRuleReader pageRuleReader;


  @Test
  void shouldGiveTheDetailsOfRule() {
    List<Object[]> targetQuestions =
        Arrays.asList(new Object[] {"label1", "test456"}, new Object[] {"label2", " test789"});
    List<Object[]> sourceQuestion = targetQuestions.subList(0, 1);
    Long ruleId = 99L;

    Mockito.when(pageRuleFinder.findByRuleId(ruleId)).thenReturn(getRuleResponse());
    Mockito.when(waQuestionRepository.findLabelByIdentifier(any())).thenReturn(sourceQuestion);
    Mockito.when(waQuestionRepository.findLabelsByIdentifiers(any())).thenReturn(targetQuestions);

    Rule result = pageRuleReader.findByRuleId(ruleId);
    assertNotNull(result);
    assertEquals("testFunction", result.function());
    assertEquals("testDescription", result.description());
    assertTrue(result.anySourceValue());
    assertEquals(2, result.targets().size());
  }

  @Test
  void shouldReturnNull() {
    Long ruleId = 99L;
    Mockito.when(pageRuleFinder.findByRuleId(ruleId)).thenReturn(null);
    Rule result = pageRuleReader.findByRuleId(ruleId);
    assertNull(result);
  }


  @Test
  void getAllPageRuleTest() {
    Long pageId = 123L;
    int page = 0;
    int size = 1;
    String sort = "id";
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

    Page<RuleResponse> ruleResponsePage = new PageImpl<>(Collections.singletonList(getRuleResponse()));
    List<Object[]> targetQuestions =
        Arrays.asList(new Object[] {"label1", "test456"}, new Object[] {"label2", " test789"});
    List<Object[]> sourceQuestion = targetQuestions.subList(0, 1);

    Mockito.when(pageRuleFinder.findByPageId(pageId, pageable)).thenReturn(ruleResponsePage);
    Mockito.when(waQuestionRepository.findLabelByIdentifier(any())).thenReturn(sourceQuestion);
    Mockito.when(waQuestionRepository.findLabelsByIdentifiers(any())).thenReturn(targetQuestions);

    Page<Rule> ruleResponse = pageRuleReader.findByPageId(pageId, pageable);
    assertNotNull(ruleResponse);
    assertEquals(1,ruleResponse.getTotalElements());
    assertEquals(1, ruleResponse.getSize());
    assertEquals(100,ruleResponse.toList().get(0).id());
    assertEquals("Question", ruleResponse.toList().get(0).targetType());
  }

    @Test
    void findPageRuleTest() {
      int page = 0;
      int size = 1;
      String sort = "id";
      Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

      Page<RuleResponse> ruleResponsePage = new PageImpl<>(Collections.singletonList(getRuleResponse()));
      List<Object[]> targetQuestions =
          Arrays.asList(new Object[] {"label1", "test456"}, new Object[] {"label2", " test789"});
      List<Object[]> sourceQuestion = targetQuestions.subList(0, 1);
      SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");

      Mockito.when(pageRuleFinder.searchPageRule(request.searchValue(), pageable)).thenReturn(ruleResponsePage);
      Mockito.when(waQuestionRepository.findLabelByIdentifier(any())).thenReturn(sourceQuestion);
      Mockito.when(waQuestionRepository.findLabelsByIdentifiers(any())).thenReturn(targetQuestions);

      Page<Rule> ruleResponse = pageRuleReader.searchPageRule(request, pageable);
      assertNotNull(ruleResponse);
      assertEquals(1,ruleResponse.getTotalElements());
      assertEquals(1, ruleResponse.getSize());
      assertEquals(100,ruleResponse.toList().get(0).id());
      assertEquals("Question", ruleResponse.toList().get(0).targetType());
    }




  RuleResponse getRuleResponse() {
    return new RuleResponse(100,
        200l,
        "testFunction",
        "testDescription",
        "test456",
        "test( )",
        "test1,test2",
        "=",
        "Question",
        "test456,test456",
        1
    );
  }

}
