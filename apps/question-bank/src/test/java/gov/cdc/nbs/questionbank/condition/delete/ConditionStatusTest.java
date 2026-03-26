package gov.cdc.nbs.questionbank.condition.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.condition.ConditionStatus;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ConditionStatusTest {
  @Mock ConditionCodeRepository conditionCodeRepository;

  @Mock LdfPageSetRepository ldfPageSetRepository;

  @InjectMocks ConditionStatus conditionStatus;

  private String id = "Statustest1234";

  ConditionStatusTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void activateConditionTest() {
    when(conditionCodeRepository.activateCondition(Mockito.anyString())).thenReturn(1);
    ConditionStatusResponse response = conditionStatus.activateCondition(id);
    ldfPageSetRepository.updateStatusBasedOnConditionCode();
    assertEquals(id, response.getId());
  }

  @Test
  void inactivateConditionTest() {
    when(conditionCodeRepository.inactivateCondition(Mockito.anyString())).thenReturn(1);
    ConditionStatusResponse response = conditionStatus.inactivateCondition(id);
    ldfPageSetRepository.updateStatusBasedOnConditionCode();
    assertEquals(id, response.getId());
  }
}
