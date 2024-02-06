package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.question.request.UpdateQuestion;
import gov.cdc.nbs.questionbank.question.request.update.*;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.model.Question.CodedQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.DataMartInfo;
import gov.cdc.nbs.questionbank.question.model.Question.DateQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.model.Question.NumericQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;

@Component
public class QuestionMapper {

    public Question toQuestion(WaQuestion entity) {
        if (entity instanceof TextQuestionEntity tq) {
            return toTextQuestion(tq);
        } else if (entity instanceof DateQuestionEntity dq) {
            return toDateQuestion(dq);
        } else if (entity instanceof NumericQuestionEntity nq) {
            return toNumericQuestion(nq);
        } else if (entity instanceof CodedQuestionEntity cq) {
            return toCodedQuestion(cq);
        } else {
            throw new UpdateQuestionException("Failed to convert entity to question");
        }
    }

    public TextQuestion toTextQuestion(TextQuestionEntity q) {
        return new TextQuestion(
            q.getId(),
            q.getMask(),
            q.getFieldSize(),
            q.getDefaultValue(),
            q.getQuestionType(),
            q.getQuestionIdentifier(),
            q.getQuestionNm(),
            q.getRecordStatusCd(),
            q.getSubGroupNm(),
            q.getDescTxt(),
            q.getDataType(),
            q.getQuestionLabel(),
            q.getQuestionToolTip(),
            q.getNbsUiComponentUid(),
            q.getAdminComment(),
            getDataMartInfo(q),
            getMessagingInfo(q));
    }

    public DateQuestion toDateQuestion(DateQuestionEntity q) {
        return new DateQuestion(
            q.getId(),
            q.getMask(),
            toBoolean(q.getFutureDateIndCd(), 'T'),
            q.getQuestionType(),
            q.getQuestionIdentifier(),
            q.getQuestionNm(),
            q.getRecordStatusCd(),
            q.getSubGroupNm(),
            q.getDescTxt(),
            q.getDataType(),
            q.getQuestionLabel(),
            q.getQuestionToolTip(),
            q.getNbsUiComponentUid(),
            q.getAdminComment(),
            getDataMartInfo(q),
            getMessagingInfo(q));
    }

    public NumericQuestion toNumericQuestion(NumericQuestionEntity q) {
        return new NumericQuestion(
            q.getId(),
            q.getMask(),
            q.getFieldSize(),
            q.getDefaultValue(),
            q.getMinValue(),
            q.getMaxValue(),
            q.getUnitTypeCd(),
            q.getUnitValue(),
            q.getQuestionType(),
            q.getQuestionIdentifier(),
            q.getQuestionNm(),
            q.getRecordStatusCd(),
            q.getSubGroupNm(),
            q.getDescTxt(),
            q.getDataType(),
            q.getQuestionLabel(),
            q.getQuestionToolTip(),
            q.getNbsUiComponentUid(),
            q.getAdminComment(),
            getDataMartInfo(q),
            getMessagingInfo(q));
    }

    public CodedQuestion toCodedQuestion(CodedQuestionEntity q) {
        return new CodedQuestion(
            q.getId(),
            q.getCodeSetGroupId(),
            q.getDefaultValue(),
            q.getQuestionType(),
            q.getQuestionIdentifier(),
            q.getQuestionNm(),
            q.getRecordStatusCd(),
            q.getSubGroupNm(),
            q.getDescTxt(),
            q.getDataType(),
            q.getQuestionLabel(),
            q.getQuestionToolTip(),
            q.getNbsUiComponentUid(),
            q.getAdminComment(),
            getDataMartInfo(q),
            getMessagingInfo(q));
    }

    private MessagingInfo getMessagingInfo(WaQuestion question) {
        return new MessagingInfo(
            toBoolean(question.getNndMsgInd(), 'T'),
            question.getQuestionIdentifierNnd(),
            question.getQuestionLabelNnd(),
            question.getQuestionOid(),
            toBoolean(question.getQuestionRequiredNnd(), 'R'),
            question.getQuestionDataTypeNnd());
    }

    private DataMartInfo getDataMartInfo(WaQuestion question) {
        return new DataMartInfo(
            question.getRptAdminColumnNm(),
            question.getRdbTableNm(),
            question.getRdbColumnNm(),
            question.getUserDefinedColumnNm());
    }

    private boolean toBoolean(Character character, Character trueValue) {
        if (character == null) {
            return false;
        } else {
            return character.equals(trueValue);
        }

    }

    public UpdateQuestion toUpdateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        if (updateQuestionRequest instanceof UpdateTextQuestionRequest tq) {
            return new UpdateQuestion(tq);
        } else if (updateQuestionRequest instanceof UpdateDateQuestionRequest dq) {
            return new UpdateQuestion(dq);
        } else if (updateQuestionRequest instanceof UpdateNumericQuestionRequest nq) {
            return new UpdateQuestion(nq);
        } else if (updateQuestionRequest instanceof UpdateCodedQuestionRequest cq) {
            return new UpdateQuestion(cq);
        } else {
            throw new UpdateQuestionException("Failed to convert entity to question");
        }
    }

}
