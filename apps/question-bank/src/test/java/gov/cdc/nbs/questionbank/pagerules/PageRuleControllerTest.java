package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.page.content.rule.PageRuleDeleter;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
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
  private PageRuleFinderService pageRuleFinderServiceImpl;

  @Mock
  private PageRuleDeleter pageRuleDeleter;


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
        .thenReturn(getViewRuleResponse());
    when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
    ViewRuleResponse ruleResponse = pageRuleController.updatePageRule(ruleId, ruleRequest, 123456L);
    assertNotNull(ruleResponse);
  }

  @Test
  void shouldReadRule() {
    Long ruleId = 99L;
    List<String> sourceValues = new ArrayList<>();
    List<QuestionInfo> targetQuestions = new ArrayList<>();
    when(pageRuleFinderServiceImpl.getRuleResponse(ruleId))
        .thenReturn(new ViewRuleResponse(ruleId, 123l, "testFunction", "testDesc", "TestINV",
            sourceValues, "=>", "TestTargetType", "testErrorMsg", targetQuestions));
    ViewRuleResponse ruleResponse = pageRuleController.viewRuleResponse(ruleId);
    assertNotNull(ruleResponse);
  }

  @Test
  void shouldReadAllRule() throws Exception {
    int page = 0;
    int size = 1;
    String sort = "id";
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
    List<String> sourceValues = new ArrayList<>();
    List<QuestionInfo> targetQuestions = new ArrayList<>();
    List<ViewRuleResponse> content = new ArrayList<>();
    content.add(new ViewRuleResponse(3546L, 123l, "testFunction", "testDesc", "TestINV",
        sourceValues, "=>", "TestTargetType", "testErrorMsg", targetQuestions));
    when(pageRuleFinderServiceImpl.getAllPageRule(pageRequest, 123456L))
        .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
    Page<ViewRuleResponse> ruleResponse = pageRuleController.getAllPageRule(pageRequest, 123456L);
    assertNotNull(ruleResponse);
  }

  @Test
  void getAllPageRuleTest() throws Exception {
    int page = 0;
    int size = 1;
    String sort = "id";
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
    List<String> sourceValues = new ArrayList<>();
    List<QuestionInfo> targetQuestions = new ArrayList<>();
    List<ViewRuleResponse> content = new ArrayList<>();
    content.add(new ViewRuleResponse(3546L, 123l, "testFunction", "testDesc", "TestINV",
        sourceValues, "=>", "TestTargetType", "testErrorMsg", targetQuestions));
    when(pageRuleFinderServiceImpl.getAllPageRule(pageRequest, 123456L))
        .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
    Page<ViewRuleResponse> ruleResponse = pageRuleController.getAllPageRule(pageRequest, 123456L);
    assertNotNull(ruleResponse);
  }

  @Test
  void findPageRuleTest() throws Exception {
    int page = 0;
    int size = 1;
    String sort = "id";
    Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
    SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");
    List<String> sourceValues = new ArrayList<>();
    List<QuestionInfo> targetQuestions = new ArrayList<>();
    List<ViewRuleResponse> content = new ArrayList<>();
    content.add(new ViewRuleResponse(3546L, 123l, "testFunction", "testDesc", "TestINV",
        sourceValues, "=>", "TestTargetType", "testErrorMsg", targetQuestions));
    when(pageRuleFinderServiceImpl.findPageRule(request, pageRequest))
        .thenReturn(new PageImpl<>(content, pageRequest, content.size()));
    Page<ViewRuleResponse> ruleResponse = pageRuleController.findPageRule(request, pageRequest);
    assertNotNull(ruleResponse);
  }

  private ViewRuleResponse getViewRuleResponse() {
    return new ViewRuleResponse(
        99l,
        123l,
        "Enable",
        "TestDescription",
        "sourceIdentifier",
        new ArrayList<>(),
        "comparator",
        "Question",
        "errorMsgText",
        Arrays.asList(new QuestionInfo("label1", "test456"), new QuestionInfo("label2", "test789"))
    );
  }

}
