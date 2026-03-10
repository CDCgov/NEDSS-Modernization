package gov.cdc.nbs.questionbank.question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest.Field;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class QuestionFinderTest {

  @Mock private WaQuestionRepository questionRepository;

  @Spy private QuestionMapper questionMapper = new QuestionMapper();

  @Mock private WaUiMetadataRepository metadatumRepository;

  @Mock private ConceptFinder conceptFinder;

  @InjectMocks private QuestionFinder finder;

  @Test
  void find_test() {
    // given a question exists
    DateQuestionEntity spy = QuestionEntityMother.dateQuestion();
    when(questionRepository.findById(1L)).thenReturn(Optional.of(spy));

    // and it is not in use
    when(metadatumRepository.findAllByQuestionIdentifier(spy.getQuestionIdentifier()))
        .thenReturn(new ArrayList<>());

    // when i try to find a question
    GetQuestionResponse response = finder.find(1L);

    // then a question is found
    assertNotNull(response);
    assertFalse(response.isInUse());
  }

  @Test
  void not_found() {
    // given a question doesn't exist

    // when i try to find a question
    // then a question not found exception is thrown
    assertThrows(QuestionNotFoundException.class, () -> finder.find(1L));
  }

  @Test
  void in_use_test() {
    // given a question exists
    DateQuestionEntity spy = QuestionEntityMother.dateQuestion();
    when(questionRepository.findById(1L)).thenReturn(Optional.of(spy));

    // and it is in use
    when(metadatumRepository.findAllByQuestionIdentifier(spy.getQuestionIdentifier()))
        .thenReturn(Collections.singletonList(new WaUiMetadata()));

    // when i try to find a question
    GetQuestionResponse response = finder.find(1L);

    // then a question is found
    assertNotNull(response);
    assertTrue(response.isInUse());
  }

  @Test
  void should_try_search_id() {
    // given a request that can be converted to an id
    FindQuestionRequest request = new FindQuestionRequest("123", "LOCAL");

    // and a question exists
    ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
    when(questionRepository.findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
            eq("123"), captor.capture(), Mockito.anyString(), Mockito.any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    // when a query is run
    finder.find(request, PageRequest.ofSize(10));

    // then the Id is queried
    assertEquals(123L, captor.getValue().longValue());
  }

  @Test
  void should_try_not_fail_if_search_not_id() {
    // given a request that can be converted to an id
    FindQuestionRequest request = new FindQuestionRequest("abc", "LOCAL");

    // and a question exists
    ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
    when(questionRepository.findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
            eq("abc"), captor.capture(), Mockito.anyString(), Mockito.any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    // when a query is run
    finder.find(request, PageRequest.ofSize(10));

    // then the Id is queried
    assertEquals(-1L, captor.getValue().longValue());
  }

  @Test
  void should_return_true_for_unique_fields() {
    when(questionRepository.findIdByQuestionIdentifier(anyString())).thenReturn(new ArrayList<>());
    when(questionRepository.findIdByQuestionNm(anyString())).thenReturn(new ArrayList<>());
    when(questionRepository.findIdByRdbColumnNm(anyString())).thenReturn(new ArrayList<>());
    when(questionRepository.findIdByUserDefinedColumnNm(anyString())).thenReturn(new ArrayList<>());

    when(conceptFinder.find(anyString())).thenReturn(getConceptList());

    assertTrue(
        finder.checkUnique(new QuestionValidationRequest(Field.UNIQUE_ID, "unique")).isValid());
    assertTrue(
        finder.checkUnique(new QuestionValidationRequest(Field.UNIQUE_NAME, "unique")).isValid());
    assertTrue(
        finder
            .checkUnique(new QuestionValidationRequest(Field.DATA_MART_COLUMN_NAME, "unique"))
            .isValid());
    assertTrue(
        finder
            .checkUnique(new QuestionValidationRequest(Field.RDB_COLUMN_NAME, "subgroup_unique"))
            .isValid());
  }

  @Test
  void should_return_false_for_duplicate_fields() {
    List<Object[]> result = new ArrayList<>();
    result.add(new Object[] {"Sample"});
    when(questionRepository.findIdByQuestionIdentifier(anyString())).thenReturn(result);
    when(questionRepository.findIdByQuestionNm(anyString())).thenReturn(result);
    when(questionRepository.findIdByRdbColumnNm(anyString())).thenReturn(result);
    when(questionRepository.findIdByUserDefinedColumnNm(anyString())).thenReturn(result);

    when(conceptFinder.find(anyString())).thenReturn(getConceptList());

    assertFalse(
        finder.checkUnique(new QuestionValidationRequest(Field.UNIQUE_ID, "duplicate")).isValid());
    assertFalse(
        finder
            .checkUnique(new QuestionValidationRequest(Field.UNIQUE_NAME, "duplicate"))
            .isValid());
    assertFalse(
        finder
            .checkUnique(new QuestionValidationRequest(Field.RDB_COLUMN_NAME, "subgroup_duplicate"))
            .isValid());
    assertFalse(
        finder
            .checkUnique(new QuestionValidationRequest(Field.DATA_MART_COLUMN_NAME, "duplicate"))
            .isValid());
  }

  @Test
  void should_throw_exception_for_invalid_subgroup() {
    QuestionValidationRequest invalidSubgroupRequest =
        new QuestionValidationRequest(Field.RDB_COLUMN_NAME, "xxx_unique");
    when(conceptFinder.find(anyString())).thenReturn(getConceptList());
    UniqueQuestionException exception =
        assertThrows(
            UniqueQuestionException.class, () -> finder.checkUnique(invalidSubgroupRequest));
    assertEquals("invalid subgroup Code", exception.getMessage());
  }

  @Test
  void should_throw_exception_for_invalid_unique_field_name() {
    QuestionValidationRequest invalidFieldRequest = new QuestionValidationRequest(null, "value");
    UniqueQuestionException exception =
        assertThrows(UniqueQuestionException.class, () -> finder.checkUnique(invalidFieldRequest));
    assertEquals("Invalid request", exception.getMessage());
  }

  @Test
  void should_throw_exception_for_invalid_unique_field_value() {
    QuestionValidationRequest invalidFieldRequest =
        new QuestionValidationRequest(Field.UNIQUE_ID, null);
    UniqueQuestionException exception =
        assertThrows(UniqueQuestionException.class, () -> finder.checkUnique(invalidFieldRequest));
    assertEquals("Invalid request", exception.getMessage());
  }

  private List<Concept> getConceptList() {
    Concept concept =
        new Concept(null, "subgroup", null, null, null, null, null, null, null, null, null, null);
    return Arrays.asList(concept);
  }
}
