package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.questionbank.condition.exception.ConditionBadRequest;
import gov.cdc.nbs.questionbank.condition.exception.ConditionInternalServerError;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;
import org.springframework.stereotype.Service;

@Service
public class ConditionStatus {

  private final ConditionCodeRepository conditionCodeRepository;
  private final LdfPageSetRepository ldfPageSetRepository;

  public ConditionStatus(
      final ConditionCodeRepository conditionCodeRepository,
      final LdfPageSetRepository ldfPageSetRepository) {
    this.conditionCodeRepository = conditionCodeRepository;
    this.ldfPageSetRepository = ldfPageSetRepository;
  }

  public ConditionStatusResponse activateCondition(String id) {
    ConditionStatusResponse response = new ConditionStatusResponse();
    if (id == null) {
      throw new ConditionBadRequest(null);
    }
    try {
      int result = conditionCodeRepository.activateCondition(id);
      response.setId(id);
      if (result == 1) {
        response.setStatusCd('A');
      } else {
        throw new ConditionInternalServerError(id);
      }
    } catch (Exception e) {
      throw new ConditionInternalServerError(id);
    }
    ldfPageSetRepository.updateStatusBasedOnConditionCode();
    return response;
  }

  public ConditionStatusResponse inactivateCondition(String id) {
    ConditionStatusResponse response = new ConditionStatusResponse();
    if (id == null) {
      throw new ConditionInternalServerError(null);
    }
    try {
      int result = conditionCodeRepository.inactivateCondition(id);
      response.setId(id);
      if (result == 1) {
        response.setStatusCd('I');
      } else {
        throw new ConditionInternalServerError(id);
      }
    } catch (Exception e) {
      throw new ConditionInternalServerError(id);
    }
    ldfPageSetRepository.updateStatusBasedOnConditionCode();
    return response;
  }
}
