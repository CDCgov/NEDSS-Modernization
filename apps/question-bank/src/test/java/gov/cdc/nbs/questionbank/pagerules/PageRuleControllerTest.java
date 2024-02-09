package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.page.content.rule.PageRuleDeleter;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageRuleControllerTest {

  @InjectMocks
  private PageRuleController pageRuleController;
  @Mock
  private PageRuleService pageRuleService;

  @Mock
  private UserDetailsProvider userDetailsProvider;

  @Mock
  private PageRuleDeleter pageRuleDeleter;

  @Mock
  PageRuleReader pageRuleReader;


  @Test
  void shouldDeleteRuleId() {
    NbsUserDetails nbsUserDetails = mock(NbsUserDetails.class);
    when(nbsUserDetails.getId()).thenReturn(300L);
    pageRuleController.deletePageRule(100L, 200L, nbsUserDetails);
    verify(pageRuleDeleter).delete(100L, 200L, nbsUserDetails.getId());
  }

  @Test
  void shouldUpdateRule() throws RuleException {
    Long ruleId = 99L;
    Long userId = 123L;
    CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
    NbsUserDetails nbsUserDetails =
        NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
    when(pageRuleService.updatePageRule(ruleId, ruleRequest, userId, 123456L))
        .thenReturn(new CreateRuleResponse(ruleId, "Rule Successfully Updated"));
    when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
    CreateRuleResponse ruleResponse = pageRuleController.updatePageRule(ruleId, ruleRequest, 123456L);
    assertNotNull(ruleResponse);
  }

  @Test
  void shouldReadRule() {
    Long ruleId = 99L;
    Rule rule = getRuleList().get(0);
    when(pageRuleReader.findByRuleId(ruleId)).thenReturn(rule);
    Rule ruleResponse = pageRuleController.viewRuleResponse(ruleId);
    assertNotNull(ruleResponse);
  }

  @Test
  void shouldReadAllRule() throws Exception {
    int page = 0;
    int size = 1;
    String sort = "id";
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
    List<Rule> content = getRuleList();
    when(pageRuleReader.findByPageId(123456L, pageRequest))
        .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
    Page<Rule> ruleResponse = pageRuleController.getAllPageRule(pageRequest, 123456L);
    assertNotNull(ruleResponse);
  }


  @Test
  void findPageRuleTest() throws Exception {
    int page = 0;
    int size = 1;
    String sort = "id";
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
    SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");
    List<Rule> content = getRuleList();
    when(pageRuleReader.searchPageRule(request, pageRequest))
        .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
    Page<Rule> ruleResponse = pageRuleController.findPageRule(request, pageRequest);
    assertNotNull(ruleResponse);
  }

  private List<Rule> getRuleList() {
    List<String> sourceValues = new ArrayList<>();
    List<QuestionInfo> targetQuestions = new ArrayList<>();
    List<Rule> content = new ArrayList<>();
    content.add(new Rule(100l, 123l, "testFunction", "testDesc", null,
        false, sourceValues, "testComparator", "testType", targetQuestions));
    return content;
  }

}
