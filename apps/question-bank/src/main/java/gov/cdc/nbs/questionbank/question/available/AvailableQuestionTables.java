package gov.cdc.nbs.questionbank.question.available;

import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.QNbsUiComponent;
import gov.cdc.nbs.questionbank.entity.question.QWaQuestion;

public record AvailableQuestionTables(
    QWaQuestion question,
    QCodeset codeset,
    QCodeValueGeneral codeValueGeneral,
    QNbsUiComponent uiComponent,
    QWaUiMetadata uiMetadata) {

  AvailableQuestionTables() {
    this(
        QWaQuestion.waQuestion,
        QCodeset.codeset,
        QCodeValueGeneral.codeValueGeneral,
        QNbsUiComponent.nbsUiComponent,
        QWaUiMetadata.waUiMetadata);
  }
}
