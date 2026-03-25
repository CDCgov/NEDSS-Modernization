package gov.cdc.nbs.questionbank.page.content.question;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaNndMetadatum;
import gov.cdc.nbs.questionbank.entity.QWaRdbMetadata;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.page.content.question.exception.FindEditableQuestionException;
import gov.cdc.nbs.questionbank.page.content.question.model.EditableQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import org.springframework.stereotype.Component;

@Component
public class EditableQuestionFinder {
  private static final QWaUiMetadata metadataTable = QWaUiMetadata.waUiMetadata;
  private static final QWaRdbMetadata rdbTable = QWaRdbMetadata.waRdbMetadata;
  private static final QWaNndMetadatum nndTable = QWaNndMetadatum.waNndMetadatum;
  private static final QCodeValueGeneral cvgTable = QCodeValueGeneral.codeValueGeneral;
  private final JPAQueryFactory factory;

  public EditableQuestionFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public EditableQuestion find(Long page, Long questionId) {
    return factory
        .select(
            // General fields
            metadataTable.questionType, // PHIN / LOCAL
            metadataTable.questionNm, // Unique name
            metadataTable.questionIdentifier, // Unique Id
            metadataTable.subGroupNm, // Subgroup Id
            metadataTable.descTxt, // Description
            metadataTable.questionLabel, // Label
            metadataTable.questionToolTip, // Tooltip
            metadataTable.nbsUiComponentUid, // Display type
            metadataTable.displayInd, // Visible
            metadataTable.enableInd, // Enabled
            metadataTable.requiredInd, // Required
            metadataTable.adminComment, // Admin comments
            metadataTable.dataType, // TEXT | NUMERIC | CODED | DATE
            // Question specific fields
            metadataTable.defaultValue, // Default value
            metadataTable.fieldSize, // Field length
            metadataTable.mask, // Mask
            metadataTable.futureDateIndCd, // Allow future dates
            metadataTable.codeSetGroupId, // Valueset
            metadataTable.minValue, // Min value
            metadataTable.maxValue, // Max value
            metadataTable.unitTypeCd, // LITERAL | CODED for related unit type
            metadataTable.unitValue, // Related units literal or Related units value set
            // Data mart
            rdbTable.rptAdminColumnNm, // Default label in report
            rdbTable.rdbTableNm, // Default RDB table name
            rdbTable.rdbColumnNm, // RDB Column name
            rdbTable.userDefinedColumnNm, // Data mart column name
            // Messaging
            nndTable.questionIdentifierNnd, // Message Id
            nndTable.questionLabelNnd, // Message label
            cvgTable.id.code, // Code system
            nndTable.questionRequiredNnd, // Required in message
            nndTable.questionDataTypeNnd, // HL7 data type
            nndTable.hl7SegmentField, // HL7 segment
            nndTable.orderGroupId // Group number
            )
        .from(metadataTable)
        .leftJoin(rdbTable)
        .on(metadataTable.id.eq(rdbTable.waUiMetadataUid.id))
        .leftJoin(nndTable)
        .on(metadataTable.id.eq(nndTable.waUiMetadataUid.id))
        .leftJoin(cvgTable)
        .on(
            metadataTable
                .questionOid
                .eq(cvgTable.codeDescTxt)
                .and(cvgTable.id.codeSetNm.eq("CODE_SYSTEM")))
        .where(metadataTable.waTemplateUid.id.eq(page).and(metadataTable.id.eq(questionId)))
        .fetch()
        .stream()
        .map(this::toEditableQuestion)
        .findFirst()
        .orElseThrow(() -> new FindEditableQuestionException("Failed to find question"));
  }

  private EditableQuestion toEditableQuestion(Tuple row) {
    return new EditableQuestion(
        "PHIN".equals(row.get(metadataTable.questionType)) ? CodeSet.PHIN : CodeSet.LOCAL,
        row.get(metadataTable.questionNm),
        row.get(metadataTable.questionIdentifier),
        row.get(metadataTable.subGroupNm),
        row.get(metadataTable.descTxt),
        row.get(metadataTable.questionLabel),
        row.get(metadataTable.questionToolTip),
        row.get(metadataTable.nbsUiComponentUid),
        "T".equals(row.get(metadataTable.displayInd)),
        "T".equals(row.get(metadataTable.enableInd)),
        "T".equals(row.get(metadataTable.requiredInd)),
        row.get(metadataTable.adminComment),
        row.get(metadataTable.dataType),
        row.get(metadataTable.defaultValue),
        tryParseInteger(row.get(metadataTable.fieldSize)),
        row.get(metadataTable.mask),
        Character.valueOf('T').equals(row.get(metadataTable.futureDateIndCd)),
        row.get(metadataTable.codeSetGroupId),
        row.get(metadataTable.minValue),
        row.get(metadataTable.maxValue),
        "LITERAL".equals(row.get(metadataTable.unitTypeCd))
            ? row.get(metadataTable.unitValue)
            : null,
        "CODED".equals(row.get(metadataTable.unitTypeCd))
            ? tryParseLong(row.get(metadataTable.unitValue))
            : null,
        toReportingInfo(row),
        toMessageInfo(row));
  }

  private Integer tryParseInteger(String value) {
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private Long tryParseLong(String value) {
    try {
      return Long.valueOf(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private ReportingInfo toReportingInfo(Tuple row) {
    return new ReportingInfo(
        row.get(rdbTable.rptAdminColumnNm),
        row.get(rdbTable.rdbTableNm),
        row.get(rdbTable.rdbColumnNm),
        row.get(rdbTable.userDefinedColumnNm));
  }

  private MessagingInfo toMessageInfo(Tuple row) {
    if (row.get(nndTable.questionIdentifierNnd) == null) {
      return new MessagingInfo(false, null, null, null, false, null);
    } else {
      return new MessagingInfo(
          row.get(nndTable.questionIdentifierNnd) != null,
          row.get(nndTable.questionIdentifierNnd),
          row.get(nndTable.questionLabelNnd),
          row.get(cvgTable.id.code),
          Character.valueOf('R')
              .equals(row.get(nndTable.questionRequiredNnd)), // O or R if required
          row.get(nndTable.questionDataTypeNnd));
    }
  }
}
