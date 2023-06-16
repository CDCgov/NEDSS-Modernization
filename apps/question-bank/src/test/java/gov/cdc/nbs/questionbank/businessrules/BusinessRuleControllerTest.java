package gov.cdc.nbs.questionbank.businessrules;

import gov.cdc.nbs.questionbank.businessrules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusinessRuleControllerTest {

    @InjectMocks
    private BusinessRuleController businessRuleController;

    @Mock
    private BusinessRuleService businessRuleService;

    @Test
    public void shouldReturnBusinessRuleResponse(){
        when(businessRuleService.createBusinessRule(new CreateRuleRequest())).thenReturn(Mockito.anyLong());
        CreateRuleResponse createRuleResponse = businessRuleController.createBusinessRule(new CreateRuleRequest());
        assertEquals(Mockito.anyLong(),createRuleResponse.templateUid());
    }

}
