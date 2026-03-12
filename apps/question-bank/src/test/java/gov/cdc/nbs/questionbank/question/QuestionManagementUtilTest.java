package gov.cdc.nbs.questionbank.question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.model.DisplayControlOptions;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionManagementUtilTest {

  @Mock private CodeValueGeneralRepository codeValueGeneralRepository;

  @Mock private WaQuestionRepository questionRepository;

  @InjectMocks private QuestionManagementUtil questionManagementUtil;

  @Test
  void should_throw_unique_exception() {
    TextQuestionEntity entity = QuestionEntityMother.textQuestion();
    // given the question is not unique
    when(questionRepository.findAllByUniqueFields(
            entity.getQuestionNm(),
            entity.getQuestionIdentifier(),
            entity.getUserDefinedColumnNm(),
            entity.getRdbColumnNm()))
        .thenReturn(Collections.singletonList(new TextQuestionEntity()));

    // when i check if a question is unique then an exception is thrown
    assertThrows(UniqueQuestionException.class, () -> questionManagementUtil.verifyUnique(entity));
  }

  @Test
  void should_not_throw_unique_exception() {
    TextQuestionEntity entity = QuestionEntityMother.textQuestion();
    // given the question is not unique
    when(questionRepository.findAllByUniqueFields(
            entity.getQuestionNm(),
            entity.getQuestionIdentifier(),
            entity.getUserDefinedColumnNm(),
            entity.getRdbColumnNm()))
        .thenReturn(new ArrayList<>());

    // when i check if a question is unique then no exception is thrown
    questionManagementUtil.verifyUnique(entity);
  }

  @Test
  void should_return_oid_for_included_request() {
    // given the codeValueGeneralRepository will return code system info
    CodeValueGeneral cvg = new CodeValueGeneral();
    cvg.setCodeDescTxt("2.16.840.1.113883.12.78");
    cvg.setCodeShortDescTxt("Abnormal flags (HL7)");
    when(codeValueGeneralRepository.findCodeSystemByCode(Mockito.anyString()))
        .thenReturn(Optional.of(cvg));

    // when I generate the question oid
    QuestionOid oid = questionManagementUtil.getQuestionOid(true, "", CodeSet.LOCAL);

    // then I am returned the proper code system info
    assertEquals(cvg.getCodeDescTxt(), oid.oid());
    assertEquals(cvg.getCodeShortDescTxt(), oid.system());
  }

  @Test
  void should_return_oid_for_not_included_local_request() {
    // given a request with messaging not included
    CreateTextQuestionRequest request = QuestionRequestMother.localTextRequest(false);

    // when I generate the question oid
    QuestionOid oid =
        questionManagementUtil.getQuestionOid(
            false, request.getMessagingInfo().codeSystem(), request.getCodeSet());

    // then I am returned the proper code system info
    assertEquals("L", oid.oid());
    assertEquals("Local", oid.system());
  }

  @Test
  void should_return_oid_for_not_included_phin_request() {
    // given a request with messaging not included
    CreateTextQuestionRequest request = QuestionRequestMother.phinTextRequest(false);

    // when I generate the question oid
    QuestionOid oid =
        questionManagementUtil.getQuestionOid(
            false, request.getMessagingInfo().codeSystem(), request.getCodeSet());

    // then I am returned the proper code system info
    assertEquals("2.16.840.1.114222.4.5.232", oid.oid());
    assertEquals("PHIN Questions", oid.system());
  }

  @Test
  void should_throw_exception_failed_to_find_code_system() {
    // given a request with an invalid code_system
    CreateTextQuestionRequest request = QuestionRequestMother.localTextRequest();
    when(codeValueGeneralRepository.findCodeSystemByCode(request.getMessagingInfo().codeSystem()))
        .thenReturn(Optional.empty());

    // when retrieving the question oid
    // then an exception is thrown
    String codeSystem = request.getMessagingInfo().codeSystem();
    CodeSet codeSet = request.getCodeSet();
    assertThrows(
        UpdateQuestionException.class,
        () -> questionManagementUtil.getQuestionOid(true, codeSystem, codeSet));
  }

  @Test
  void should_return_displayControlOptions() {
    DisplayControlOptions displayControlOptions = questionManagementUtil.getDisplayControlOptions();
    assertNotNull(displayControlOptions);
    assertFalse(displayControlOptions.codedDisplayControl().isEmpty());
    assertFalse(displayControlOptions.dateDisplayControl().isEmpty());
    assertFalse(displayControlOptions.numericDisplayControl().isEmpty());
    assertFalse(displayControlOptions.textDisplayControl().isEmpty());
  }
}
