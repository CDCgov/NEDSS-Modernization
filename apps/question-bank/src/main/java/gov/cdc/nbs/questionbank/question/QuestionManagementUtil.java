package gov.cdc.nbs.questionbank.question;

import java.util.List;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
public class QuestionManagementUtil {

    private final CodeValueGeneralRepository codeValueGeneralRepository;
    private final WaQuestionRepository questionRepository;

    public QuestionManagementUtil(
            CodeValueGeneralRepository codeValueGeneralRepository,
            WaQuestionRepository questionRepository) {
        this.codeValueGeneralRepository = codeValueGeneralRepository;
        this.questionRepository = questionRepository;
    }

    QuestionOid getQuestionOid(boolean includedInMessage, String codeSystem, String codeSet) {
        if (includedInMessage) {
            return codeValueGeneralRepository.findByCode(
                    codeSystem)
                    .stream()
                    .map(cvg -> new QuestionOid(
                            cvg.getCodeDescTxt(),
                            cvg.getCodeShortDescTxt()))
                    .findFirst()
                    .orElseThrow(() -> new UpdateQuestionException(
                            "Failed to find 'CODE_SYSTEM' for code: " + codeSystem));
        } else {
            return switch (codeSet) {
                case "PHIN" -> new QuestionOid("2.16.840.1.114222.4.5.232", "PHIN Questions");
                case "LOCAL" -> new QuestionOid("L", "Local");
                default -> null;
            };
        }
    }

    /**
     * Verifies the new question will not conflict with any existing questions.
     * 
     * The following fields must be unique but are not defined as unique in the databse:
     * 
     * questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm
     * 
     * @param question
     */
    void verifyUnique(WaQuestion question) {
        List<WaQuestion> conflicts = questionRepository.findAllByUniqueFields(
                question.getQuestionNm(),
                question.getQuestionIdentifier(),
                question.getUserDefinedColumnNm(),
                question.getRdbColumnNm());
        if (!conflicts.isEmpty()) {
            throw new UniqueQuestionException(
                    "One of the following fields was not unique: questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm");
        }
    }
}
