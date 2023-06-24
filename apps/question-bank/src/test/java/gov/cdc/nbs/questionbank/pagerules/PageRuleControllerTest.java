package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class PageRuleControllerTest {

    @InjectMocks
    private PageRuleController pageRuleController;
    @Mock
    private PageRuleService pageRuleService;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @Test
    public void shouldReturnCreateRuleResponse(){

        CreateRuleRequest.ruleRequest ruleRequest = RuleRequestMother.ruleRequest();
        NbsUserDetails nbsUserDetails=  NbsUserDetails.builder().id(123L).firstName("test user").lastName("test").build();
        Mockito.when(pageRuleService.createPageRule(nbsUserDetails.getId(), ruleRequest)).thenReturn(new CreateRuleResponse(999L,"Rule Created Successfully"));
        Mockito.when(userDetailsProvider.getCurrentUserDetails()).thenReturn(nbsUserDetails);
        CreateRuleResponse ruleResponse = pageRuleController.createBusinessRule(ruleRequest);
        assertEquals(999L,ruleResponse.ruleId());



    }
}
