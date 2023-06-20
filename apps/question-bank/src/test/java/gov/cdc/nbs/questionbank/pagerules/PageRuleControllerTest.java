package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PageRuleControllerTest {

    @InjectMocks
    private PageRuleController pageRuleController;
    @Mock
    private PageRuleService pageRuleService;

    @Test
    public void shouldReturnCreateRuleResponse(){

        CreateRuleRequest.ruleRequest ruleRequest = RuleRequestMother.ruleRequest();
        Mockito.when(pageRuleService.createPageRule(ruleRequest)).thenReturn(19L);

        CreateRuleResponse ruleResponse = pageRuleController.createBusinessRule(ruleRequest);

        Assert.assertEquals(19L, ruleResponse.ruleId());

    }
}
