package gov.cdc.nbs.questionbank.condition.command;

public sealed interface ConditionCommand {
  public record AddCondition(
      String id,
      long nbsUid,
      String codeSystemDescTxt,
      String conditionShortNm,
      Character nndInd,
      String progAreaCd,
      Character reportableMorbidityInd,
      Character reportableSummaryInd,
      Character contactTracingEnableInd,
      String familyCd,
      String coinfectionGrpCd,
      long userId)
      implements ConditionCommand {}
}
