package gov.cdc.nbs.questionbank.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.time.Instant;

import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;



public class ConditionCreatorTest {

    @Mock
    ConditionCodeRepository conditionRepository;

    @InjectMocks
    ConditionCreator conditionCreator;

    private static final long userId = 1L;
    public ConditionCreatorTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createConditionTest() {
        Condition condition = conditionCreator.createCondition(userId);
        ConditionCode saveCondition = new ConditionCode(conditionCreator.conditionAdd(request, userId));
        when(conditionCodeRepository.save(Mockito.any(ConditionCode.class))).thenReturn(saveCondition);
        CreateConditionResponse response = conditionCreator.createCondition(request, userId);
        assertNotNull(condition);
        assertEquals(userId, condition.getUserId());
        assertEquals(saveCondition.getId(), response.getId());
        assertEquals(HttpStatus.CREATED);

    }

    //tests need to be fixed after conditioncreator is verified
    
}
