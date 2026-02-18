package gov.cdc.nbs.questionbank.question.available;

import com.querydsl.core.Tuple;

public class AvailableQuestionMapper {

  private static final AvailableQuestionTables tables = new AvailableQuestionTables();

  private AvailableQuestionMapper() {}

  public static AvailableQuestion toQuestion(Tuple tuple) {
    return new AvailableQuestion(
        tuple.get(tables.question().id),
        tuple.get(tables.question().questionType),
        tuple.get(tables.question().recordStatusCd),
        tuple.get(tables.question().dataType),
        Character.valueOf('T').equals(tuple.get(tables.question().otherValueIndCd)),
        tuple.get(tables.question().questionIdentifier),
        tuple.get(tables.question().subGroupNm),
        tuple.get(tables.codeValueGeneral().codeShortDescTxt),
        tuple.get(tables.question().codeSetGroupId),
        tuple.get(tables.codeset().valueSetNm),
        tuple.get(tables.question().questionNm),
        tuple.get(tables.question().descTxt),

        // UI info
        tuple.get(tables.question().questionLabel),
        tuple.get(tables.question().questionToolTip),
        tuple.get(tables.question().nbsUiComponentUid),
        tuple.get(tables.uiComponent().typeCdDesc),

        // Datamart - Reporting
        tuple.get(tables.question().rptAdminColumnNm),
        tuple.get(tables.question().rdbTableNm),
        tuple.get(tables.question().rdbColumnNm),
        tuple.get(tables.question().userDefinedColumnNm),

        // Messaging
        Character.valueOf('T').equals(tuple.get(tables.question().nndMsgInd)),
        tuple.get(tables.question().questionIdentifierNnd),
        tuple.get(tables.question().questionOidSystemTxt),
        Character.valueOf('T').equals(tuple.get(tables.question().questionRequiredNnd)),
        tuple.get(tables.question().questionDataTypeNnd),
        tuple.get(tables.question().hl7SegmentField),

        // Admin
        tuple.get(tables.question().adminComment));
  }
}
