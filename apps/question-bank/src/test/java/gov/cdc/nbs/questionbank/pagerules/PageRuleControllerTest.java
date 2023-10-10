package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PageRuleControllerTest {

    @InjectMocks
    private PageRuleController pageRuleController;
    @Mock
    private PageRuleService pageRuleService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    void shouldReturnCreateRuleResponse() throws Exception {
        CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.createPageRule(nbsUserDetails.getId(), ruleRequest))
                .thenReturn(new CreateRuleResponse(999L, "Rule Created Successfully"));
        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
        CreateRuleResponse ruleResponse = pageRuleController.createBusinessRule(ruleRequest);
        assertEquals(999L, ruleResponse.ruleId());

        Mockito.when(pageRuleService.createPageRule(nbsUserDetails.getId(), ruleRequest))
                .thenThrow(new RuleException("",0));
        ruleResponse = pageRuleController.createBusinessRule(ruleRequest);
        assertNull(ruleResponse.ruleId());

    }


    @Test
    void shouldDeleteRuleId() {
        Long ruleId = 99L;
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.deletePageRule(99L))
                .thenReturn(new CreateRuleResponse(ruleId, "Rule Successfully Deleted"));
        CreateRuleResponse ruleResponse = pageRuleController.deletePageRule(ruleId);
        assertNotNull(ruleResponse);
    }

    @Test
    void shouldUpdateRule() throws RuleException {
        Long ruleId = 99L;
        Long userId = 123L;
        CreateRuleRequest ruleRequest = RuleRequestMother.ruleRequest();
        NbsUserDetails nbsUserDetails =
                NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.updatePageRule(ruleId, ruleRequest, userId))
                .thenReturn(new CreateRuleResponse(ruleId, "Rule Successfully Updated"));
        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
        CreateRuleResponse ruleResponse = pageRuleController.updatePageRule(ruleId, ruleRequest);
        assertNotNull(ruleResponse);
    }

    @Test
    void shouldReadRule() {
        Long ruleId = 99L;
        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        Mockito.when(pageRuleService.getRuleResponse(ruleId))
                .thenReturn(new ViewRuleResponse(ruleId, 123l, "testFunction", "testDesc", "TestINV",
                        sourceValues, "=>", "TestTargetType", "testErrorMsg", targetValues));
        ViewRuleResponse ruleResponse = pageRuleController.viewRuleResponse(ruleId);
        assertNotNull(ruleResponse);
    }
    @Test
    void shouldReadAllRule() throws Exception {
        int page = 0;
        int size =1;
        String sort ="id";
        Pageable pageRequest = PageRequest.of(page,size,Sort.by(sort));
        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        List<ViewRuleResponse> content = new ArrayList<>();
        content.add(new ViewRuleResponse(3546L, 123l, "testFunction", "testDesc", "TestINV",
                sourceValues, "=>", "TestTargetType", "testErrorMsg", targetValues));

        Mockito.when(pageRuleService.getAllPageRule(pageRequest)).
        thenReturn(new PageImpl<>(content,pageRequest,content.size()));
        Page<ViewRuleResponse> ruleResponse = pageRuleController.getAllPageRule(pageRequest);
        assertNotNull(ruleResponse);
    }

    @Test
   void getAllPageRuleTest() throws Exception {
        int page = 0;
        int size =1;
        String sort ="id";
        Pageable pageRequest = PageRequest.of(page,size,Sort.by(sort));
        List<String> sourceValues = new ArrayList<>();
        List<String> targetValues = new ArrayList<>();
        List<ViewRuleResponse> content = new ArrayList<>();
        content.add(new ViewRuleResponse(3546L, 123l, "testFunction", "testDesc", "TestINV",
                sourceValues, "=>", "TestTargetType", "testErrorMsg", targetValues));

        Mockito.when(pageRuleService.getAllPageRule(pageRequest)).
                thenReturn(new PageImpl<>(content,pageRequest,content.size()));
        Page<ViewRuleResponse> ruleResponse = pageRuleController.getAllPageRule(pageRequest);
        assertNotNull(ruleResponse);
    }
}
