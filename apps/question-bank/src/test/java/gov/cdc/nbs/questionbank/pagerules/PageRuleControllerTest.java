package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.BadAttributeValueExpException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PageRuleControllerTest {

    @InjectMocks
    private PageRuleController pageRuleController;
    @Mock
    private PageRuleService pageRuleService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    public void shouldReturnCreateRuleResponse() throws Exception {
        CreateRuleRequest.ruleRequest ruleRequest = RuleRequestMother.ruleRequest();
        NbsUserDetails nbsUserDetails=  NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.createPageRule(nbsUserDetails.getId(), ruleRequest)).thenReturn(new CreateRuleResponse(999L,"Rule Created Successfully"));
        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
        CreateRuleResponse ruleResponse = pageRuleController.createBusinessRule(ruleRequest);
        assertEquals(999L,ruleResponse.ruleId());
    }


    @Test
    public void shouldDeleteRuleId(){
        Long ruleId= 99L;
        NbsUserDetails nbsUserDetails=  NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.deletePageRule(99L)).thenReturn(new CreateRuleResponse(ruleId,"Rule Successfully Deleted"));
        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
        CreateRuleResponse ruleResponse= pageRuleController.deletePageRule(ruleId);
        assertNotNull(ruleResponse);
    }

    @Test
    public void shouldUpdateRule() throws BadAttributeValueExpException {
        Long ruleId= 99L;
        Long userId= 123L;
        CreateRuleRequest.ruleRequest ruleRequest = RuleRequestMother.ruleRequest();
        NbsUserDetails nbsUserDetails=  NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.updatePageRule(ruleId,ruleRequest,userId)).thenReturn(new CreateRuleResponse(ruleId,"Rule Successfully Updated"));
        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
        CreateRuleResponse ruleResponse= pageRuleController.updatePageRule(ruleId,ruleRequest);
        assertNotNull(ruleResponse);
    }

    @Test
    public void shouldReadRule(){
        Long ruleId= 99L;
        List<String> sourceValues= new ArrayList<>();
        List<String> targetValues= new ArrayList<>();
        Mockito.when(pageRuleService.getRuleResponse(ruleId)).thenReturn(new ViewRuleResponse.ruleResponse(ruleId,123l,"testFunction","testDesc","TestINV",sourceValues,"=>","TestTargetType","testErrorMsg",targetValues));
        ViewRuleResponse.ruleResponse ruleResponse= pageRuleController.viewRuleResponse(ruleId);
        assertNotNull(ruleResponse);
    }

}
