package gov.cdc.nbs.questionbank.condition;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNotEmpty;

import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.exception.ConditionCreateException;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConditionCreator {

  private final ConditionCodeRepository conditionCodeRepository;
  private final LdfPageSetRepository ldfPageSetRepository;
  private final Clock clock;

  public ConditionCreator(
      final ConditionCodeRepository conditionCodeRepository,
      final LdfPageSetRepository ldfPageSetRepository,
      final Clock clock) {
    this.conditionCodeRepository = conditionCodeRepository;
    this.ldfPageSetRepository = ldfPageSetRepository;
    this.clock = clock;
  }

  public Condition createCondition(CreateConditionRequest request, long userId) {
    requireNotEmpty(request.conditionShortNm(), "Condition Name");
    requireNotEmpty(request.codeSystemDescTxt(), "Coding System");
    requireNotEmpty(request.code(), "Condition Code");
    requireNotEmpty(request.progAreaCd(), "Program Area");

    long nbsUid = conditionCodeRepository.getNextNbsUid();

    // check if id already exists
    if (checkId(request.code())) {
      throw new ConditionCreateException("Condition Code already exists");
    }

    // check if conditionNm already exists
    if (checkConditionNm(request.conditionShortNm())) {
      throw new ConditionCreateException("Condition Name already exists");
    }

    try {
      ConditionCode conditionCode = new ConditionCode(conditionAdd(request, userId, nbsUid));
      conditionCode
          .getLdfPageSets()
          .add(
              new LdfPageSet(
                  conditionCode,
                  getLdfId(),
                  ldfPageSetRepository.nextDisplayRow(),
                  ldfPageSetRepository.nextNbsUid()));
      conditionCode = conditionCodeRepository.save(conditionCode);
      return new Condition(
          conditionCode.getId(),
          conditionCode.getConditionShortNm(),
          conditionCode.getProgAreaCd(),
          conditionCode.getFamilyCd(),
          conditionCode.getCoinfectionGrpCd(),
          conditionCode.getNndInd(),
          conditionCode.getInvestigationFormCd(),
          conditionCode.getStatusCd());

    } catch (Exception e) {
      throw new ConditionCreateException("Failed to create condition");
    }
  }

  public boolean checkId(String id) {
    return (id != null && conditionCodeRepository.checkId(id) > 0);
  }

  public boolean checkConditionNm(String conditionNm) {
    return (conditionNm != null && conditionCodeRepository.checkConditionName(conditionNm) > 0);
  }

  public String getLdfId() {
    List<String> allIds = ldfPageSetRepository.findAllIds();
    String currentYear = String.valueOf(LocalDate.now(clock).getYear());
    int numericPart = 0;

    if (!allIds.isEmpty()) {
      numericPart =
          allIds.stream()
              .filter(id -> id.startsWith(currentYear))
              .mapToInt(id -> Integer.parseInt(id.substring(4)))
              .max()
              .orElse(0);
    }
    numericPart++;
    return currentYear + "%03d".formatted(numericPart);
  }

  public ConditionCommand.AddCondition conditionAdd(
      final CreateConditionRequest request, long userId, long nbsUid) {
    return new ConditionCommand.AddCondition(
        request.code(),
        nbsUid,
        request.codeSystemDescTxt(),
        request.conditionShortNm(),
        request.nndInd(),
        request.progAreaCd(),
        request.reportableMorbidityInd(),
        request.reportableSummaryInd(),
        request.contactTracingEnableInd(),
        request.familyCd(),
        request.coinfectionGrpCd(),
        userId);
  }
}
