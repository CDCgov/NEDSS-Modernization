package gov.cdc.nbs.questionbank.condition.command;

import java.time.Instant;

public sealed interface ConditionCommand {
    public record AddCondition(
            String id,
            String codeSystemDescTxt,
            String conditionShortNm,
            Character nndInd,
            String progAreaCd,
            Character reportableMorbidityInd,
            Character reportableSummaryInd,
            Character contactTracingEnableInd,
            String familyCd,
            String coinfectionGrpCd, long userId) implements ConditionCommand {
    }

}
