package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest.Field;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import gov.cdc.nbs.questionbank.question.response.QuestionValidationResponse;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class QuestionFinder {
  private final WaQuestionRepository questionRepository;
  private final WaUiMetadataRepository uiMetadataRepository;
  private final QuestionMapper questionMapper;
  private final ConceptFinder conceptFinder;

  private static final String SUBGROUP_CODE_SET = "NBS_QUES_SUBGROUP";

  public QuestionFinder(
      final WaQuestionRepository questionRepository,
      final QuestionMapper questionMapper,
      WaUiMetadataRepository uiMetadataRepository,
      ConceptFinder conceptFinder) {
    this.questionRepository = questionRepository;
    this.questionMapper = questionMapper;
    this.uiMetadataRepository = uiMetadataRepository;
    this.conceptFinder = conceptFinder;
  }

  public GetQuestionResponse find(Long id) {
    Question question =
        questionRepository
            .findById(id)
            .map(questionMapper::toQuestion)
            .orElseThrow(() -> new QuestionNotFoundException(id));
    boolean isInUse = checkQuestionInUse(question.uniqueId());
    return new GetQuestionResponse(question, isInUse);
  }

  public Page<Question> find(Pageable pageable) {
    Page<WaQuestion> page = questionRepository.findAll(pageable);
    List<Question> questions = page.get().map(questionMapper::toQuestion).toList();
    return new PageImpl<>(questions, pageable, page.getTotalElements());
  }

  public Page<Question> find(FindQuestionRequest request, Pageable pageable) {
    Page<WaQuestion> page =
        questionRepository.findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
            request.search(), tryConvert(request.search()), request.questionType(), pageable);
    List<Question> questions = page.get().map(questionMapper::toQuestion).toList();
    return new PageImpl<>(questions, pageable, page.getTotalElements());
  }

  public boolean checkQuestionInUse(String identifier) {
    return !uiMetadataRepository.findAllByQuestionIdentifier(identifier).isEmpty();
  }

  Long tryConvert(String search) {
    try {
      return Long.valueOf(search);
    } catch (NumberFormatException e) {
      return -1L;
    }
  }

  public QuestionValidationResponse checkUnique(QuestionValidationRequest request) {
    if (request.value() == null || request.field() == null) {
      throw new UniqueQuestionException("Invalid request");
    }
    if (Field.UNIQUE_ID.equals(request.field()))
      return new QuestionValidationResponse(
          questionRepository.findIdByQuestionIdentifier(request.value()).isEmpty());
    if (Field.UNIQUE_NAME.equals(request.field()))
      return new QuestionValidationResponse(
          questionRepository.findIdByQuestionNm(request.value()).isEmpty());
    if (Field.DATA_MART_COLUMN_NAME.equals(request.field()))
      return new QuestionValidationResponse(
          questionRepository.findIdByUserDefinedColumnNm(request.value()).isEmpty());
    if (Field.RDB_COLUMN_NAME.equals(request.field())) {
      checkSubGroupValidity(request.value());
      return new QuestionValidationResponse(
          questionRepository.findIdByRdbColumnNm(request.value()).isEmpty());
    }
    throw new UniqueQuestionException("invalid unique field name");
  }

  private void checkSubGroupValidity(String rdbColumnName) {
    String subGroupCode = rdbColumnName.substring(0, rdbColumnName.indexOf("_"));
    boolean isMatch =
        conceptFinder.find(SUBGROUP_CODE_SET).stream()
            .map(Concept::localCode)
            .anyMatch(code -> code.equals(subGroupCode));
    if (!isMatch) throw new UniqueQuestionException("invalid subgroup Code");
  }
}
