package gov.cdc.nbs.questionbank.condition.delete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.condition.ConditionStatus;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;

import java.util.Objects;

class ConditionStatusTest {
    @Mock
    ConditionCodeRepository conditionCodeRepository;

    @InjectMocks
    ConditionStatus conditionStatus;

    private String id = "Statustest1234";

    ConditionStatusTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void activateConditionTest() {
        when(conditionCodeRepository.activateCondition(Mockito.anyString())).thenReturn(1);
        ConditionStatusResponse response = conditionStatus.activateCondition(id);
        assertEquals(id, response.getId());
    }

    

    @Test
    void inactivateConditionTest() {
        when(conditionCodeRepository.inactivateCondition(Mockito.anyString())).thenReturn(1);
        ConditionStatusResponse response = conditionStatus.inactivateCondition(id);
        assertEquals(id, response.getId());
    }



}
