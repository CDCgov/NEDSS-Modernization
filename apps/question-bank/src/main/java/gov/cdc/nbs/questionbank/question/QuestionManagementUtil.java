package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.model.DisplayControlOptions;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

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

  public QuestionOid getQuestionOid(boolean includedInMessage, String codeSystem, CodeSet codeSet) {
    if (includedInMessage) {
      return codeValueGeneralRepository.findCodeSystemByCode(codeSystem).stream()
          .map(cvg -> new QuestionOid(cvg.getCodeDescTxt(), cvg.getCodeShortDescTxt()))
          .findFirst()
          .orElseThrow(
              () ->
                  new UpdateQuestionException(
                      "Failed to find 'CODE_SYSTEM' for code: " + codeSystem));
    } else {
      return switch (codeSet) {
        case PHIN -> new QuestionOid("2.16.840.1.114222.4.5.232", "PHIN Questions");
        case LOCAL -> new QuestionOid("L", "Local");
        default -> null;
      };
    }
  }

  /**
   * Verifies the new question will not conflict with any existing questions.
   *
   * <p>The following fields must be unique but are not defined as unique in the database:
   *
   * <p>questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm
   *
   * @param question
   */
  public void verifyUnique(WaQuestion question) {
    List<WaQuestion> conflicts =
        questionRepository.findAllByUniqueFields(
            question.getQuestionNm(),
            question.getQuestionIdentifier(),
            question.getUserDefinedColumnNm(),
            question.getRdbColumnNm());
    if (!conflicts.isEmpty()) {
      throw new UniqueQuestionException(
          "One of the following fields was not unique: questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm");
    }
  }

  public DisplayControlOptions getDisplayControlOptions() {
    return new DisplayControlOptions(
        CreateCodedQuestionRequest.CodedDisplayControl.getDisplayOptions(),
        CreateDateQuestionRequest.DateDisplayControl.getDisplayOptions(),
        CreateNumericQuestionRequest.NumericDisplayControl.getDisplayOptions(),
        CreateTextQuestionRequest.TextDisplayControl.getDisplayOptions());
  }

  public List<Long> getQuestionNbsUiComponentUids() {
    return Stream.concat(
            Stream.concat(
                    getDisplayControlOptions().codedDisplayControl().stream(),
                    getDisplayControlOptions().numericDisplayControl().stream())
                .map(c -> c.value()),
            Stream.concat(
                    getDisplayControlOptions().textDisplayControl().stream(),
                    getDisplayControlOptions().dateDisplayControl().stream())
                .map(c -> c.value()))
        .toList();
  }
}
