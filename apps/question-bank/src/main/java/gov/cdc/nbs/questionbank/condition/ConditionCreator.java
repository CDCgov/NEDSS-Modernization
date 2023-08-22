package gov.cdc.nbs.questionbank.condition;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import gov.cdc.nbs.questionbank.condition.exception.ConditionCreateException;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ConditionCreator {

    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    @Autowired
    private LdfPageSetRepository ldfPageSetRepository;

    public Condition createCondition(CreateConditionRequest request, long userId) {
        String conditionNm = request.conditionShortNm();
        long nbsUid = conditionCodeRepository.getNextNbsUid();

        //check if id already exists
        if (checkId(request.code())) {
            throw new ConditionCreateException("Condition Id already exists");
        }

        //check if conditionNm already exists
        if (checkConditionNm(conditionNm)) {
            throw new ConditionCreateException("Condition Name already exists");
        }

        try {
            ConditionCode conditionCode = new ConditionCode(conditionAdd(request, userId, nbsUid));
            conditionCode.getLdfPageSets().add(
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
        String currentYear = String.valueOf(LocalDate.now().getYear());
        int numericPart = 0;

        if (!allIds.isEmpty()) {
            numericPart = allIds.stream()
                    .filter(id -> id.startsWith(currentYear))
                    .mapToInt(id -> Integer.parseInt(id.substring(4)))
                    .max()
                    .orElse(0);
        }
        numericPart++;
        return currentYear + String.format("%03d", numericPart);
    }

    public ConditionCommand.AddCondition conditionAdd(final CreateConditionRequest request, long userId, long nbsUid) {
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
