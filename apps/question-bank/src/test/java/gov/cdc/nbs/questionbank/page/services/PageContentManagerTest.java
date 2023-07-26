package gov.cdc.nbs.questionbank.page.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.page.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class PageContentManagerTest {

    @Mock
    private WaQuestionRepository questionRepository;

    @Mock
    private WaUiMetadatumRepository uiMetadatumRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PageContentManager contentManager;

    @Test
    void should_add_question_to_page() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 12);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // and a valid question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(questionRepository.findById(request.questionId())).thenReturn(Optional.of(textQuestion));

        // and a valid userId
        Long userId = 999L;

        // and the question is not already associated with the page
        when(uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, textQuestion.getQuestionIdentifier()))
                .thenReturn(0L);

        // when an add question request is processed
        ArgumentCaptor<WaUiMetadatum> captor = ArgumentCaptor.forClass(WaUiMetadatum.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadatum savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });
        Long newId = contentManager.addQuestion(pageId, request, userId);

        // then the question is added to the page
        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertNotNull(newId);
    }

    @Test
    void should_not_allow_duplicate_question() {
        // given a request to add a question to a page
        var request = new AddQuestionRequest(123L, 12);

        // and a valid page id
        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        // and a valid question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(questionRepository.findById(request.questionId())).thenReturn(Optional.of(textQuestion));

        // and a valid userId
        Long userId = 999L;

        // and the question already exists on the page
        when(uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, textQuestion.getQuestionIdentifier()))
                .thenReturn(1L);

        // when an add question request is processed 
        // then an exception is thrown
        assertThrows(AddQuestionException.class, () -> contentManager.addQuestion(pageId, request, userId));
        verify(uiMetadatumRepository, times(0)).save(Mockito.any());
    }
}
