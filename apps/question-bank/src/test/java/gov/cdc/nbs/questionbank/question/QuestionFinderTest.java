package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
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
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class QuestionFinderTest {

    @Mock
    private WaQuestionRepository questionRepository;

    @Spy
    private QuestionMapper questionMapper = new QuestionMapper();

    @Mock
    private WaUiMetadataRepository metadatumRepository;

    @InjectMocks
    private QuestionFinder finder;

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
        FindQuestionRequest request = new FindQuestionRequest("123");

        // and a question exists
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(questionRepository.findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
            eq("123"),
            captor.capture(),
            Mockito.any()))
          .thenReturn(new PageImpl<>(new ArrayList<>()));

        // when a query is run
        finder.find(request, PageRequest.ofSize(10));

        // then the Id is queried
        assertEquals(123L, captor.getValue().longValue());
    }

    @Test
    void should_try_not_fail_if_search_not_id() {
        // given a request that can be converted to an id
        FindQuestionRequest request = new FindQuestionRequest("abc");

        // and a question exists
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(questionRepository.findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
            eq("abc"),
            captor.capture(),
            Mockito.any()))
          .thenReturn(new PageImpl<>(new ArrayList<>()));

        // when a query is run
        finder.find(request, PageRequest.ofSize(10));

        // then the Id is queried
        assertEquals(-1L, captor.getValue().longValue());
    }
}
