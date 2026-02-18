package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ConditionReader {

  private final ConditionCodeRepository conditionCodeRepository;

  public ConditionReader(final ConditionCodeRepository conditionCodeRepository) {
    this.conditionCodeRepository = conditionCodeRepository;
  }

  public Page<Condition> findConditions(Pageable pageable) {
    Page<ConditionCode> result = conditionCodeRepository.findAll(pageable);
    List<Condition> resultToList = readCondition(result);
    return new PageImpl<>(resultToList, pageable, result.getTotalElements());
  }

  public List<Condition> readCondition(Page<ConditionCode> result) {
    List<Condition> results = new ArrayList<>();
    for (ConditionCode conditionCode : result.getContent()) {
      results.add(
          new Condition(
              conditionCode.getId(),
              conditionCode.getConditionShortNm(),
              conditionCode.getProgAreaCd(),
              conditionCode.getFamilyCd(),
              conditionCode.getCoinfectionGrpCd(),
              conditionCode.getNndInd(),
              conditionCode.getInvestigationFormCd(),
              conditionCode.getStatusCd()));
    }
    return results;
  }

  public List<Condition> findAllConditions() {
    var conditions =
        conditionCodeRepository.findAll(Sort.by(Sort.Direction.ASC, "conditionShortNm"));
    return conditions.stream()
        .map(
            c ->
                new Condition(
                    c.getId(),
                    c.getConditionShortNm(),
                    c.getProgAreaCd(),
                    c.getFamilyCd(),
                    c.getCoinfectionGrpCd(),
                    c.getNndInd(),
                    c.getInvestigationFormCd(),
                    c.getStatusCd()))
        .toList();
  }
}
