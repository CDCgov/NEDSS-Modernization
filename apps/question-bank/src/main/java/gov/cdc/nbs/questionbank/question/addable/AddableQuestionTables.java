package gov.cdc.nbs.questionbank.question.addable;

import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.entity.QNbsUiComponent;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.QWaQuestion;

public record AddableQuestionTables(
    QWaQuestion question,
    QCodeset codeset,
    QCodeValueGeneral codeValueGeneral,
    QNbsUiComponent uiComponent,
    QWaUiMetadata uiMetadata) {

  AddableQuestionTables() {
    this(
        QWaQuestion.waQuestion,
        QCodeset.codeset,
        QCodeValueGeneral.codeValueGeneral,
        QNbsUiComponent.nbsUiComponent,
        QWaUiMetadata.waUiMetadata);
  }
}
