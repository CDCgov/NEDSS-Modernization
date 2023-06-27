package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class QuestionFinderTest {

    @Mock
    private WaQuestionRepository questionRepository;

    @Spy
    private QuestionMapper questionMapper = new QuestionMapper();

    @Mock
    private WaUiMetadatumRepository metadatumRepository;

    @InjectMocks
    private QuestionFinder finder;

    @Test
    void find_test() {
        // given a question exists
        when(questionRepository.findById(1L)).thenReturn(Optional.of(QuestionEntityMother.dateQuestion()));
        
        // and it is not in use
        when(metadatumRepository.findAllByQuestionIdentifier(QuestionEntityMother.dateQuestion().getQuestionIdentifier()))
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
        when(questionRepository.findById(1L)).thenReturn(Optional.of(QuestionEntityMother.dateQuestion()));
        
        // and it is in use
        when(metadatumRepository.findAllByQuestionIdentifier(QuestionEntityMother.dateQuestion().getQuestionIdentifier()))
            .thenReturn(Collections.singletonList(new WaUiMetadatum()));

        // when i try to find a question
        GetQuestionResponse response = finder.find(1L);

        // then a question is found
        assertNotNull(response);
        assertTrue(response.isInUse());
    }
}
