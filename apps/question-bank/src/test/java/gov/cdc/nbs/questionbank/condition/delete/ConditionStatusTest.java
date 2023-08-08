package gov.cdc.nbs.questionbank.condition.delete;

import static org.junit.Assert.assertEquals;
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
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void activateConditionFailTest() {
        when(conditionCodeRepository.activateCondition(Mockito.anyString())).thenReturn(0);
        ConditionStatusResponse response = conditionStatus.activateCondition(id);
        assertEquals(id, response.getId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    void activateConditionNullTest() {
        ConditionStatusResponse response = conditionStatus.activateCondition(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void inactivateConditionTest() {
        when(conditionCodeRepository.inactivateCondition(Mockito.anyString())).thenReturn(1);
        ConditionStatusResponse response = conditionStatus.inactivateCondition(id);
        assertEquals(id, response.getId());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void inactivateConditionFailTest() {
        when(conditionCodeRepository.inactivateCondition(Mockito.anyString())).thenReturn(0);
        ConditionStatusResponse response = conditionStatus.inactivateCondition(id);
        assertEquals(id, response.getId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    void inactivateNullTest() {
        ConditionStatusResponse response = conditionStatus.inactivateCondition(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }
}
