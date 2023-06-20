package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@ExtendWith(MockitoExtension.class)
class QuestionUpdaterTest {

    @Mock
    private WaQuestionRepository repository;

    @Spy
    private QuestionMapper questionMapper = new QuestionMapper();

    @InjectMocks
    private QuestionUpdater updater;

    @Test
    void should_set_status_inactive() {
        // given an active question and a working repository
        when(repository.findById(321L)).thenReturn(Optional.of(emptyQuestion()));
        ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
        when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

        // when a set status to inactive request is processed
        updater.setStatus(9L, 321L, false);

        // then the question should have inactive status
        WaQuestion question = captor.getValue();
        assertNotNull(question);
        assertEquals("Inactive", question.getRecordStatusCd());
    }

    @Test
    void should_set_status_active() {
        // given an active question and a working repository
        when(repository.findById(321L)).thenReturn(Optional.of(emptyQuestion()));
        ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
        when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

        // when a set status to inactive request is processed
        updater.setStatus(9L, 321L, true);

        // then the question should have inactive status
        WaQuestion question = captor.getValue();
        assertNotNull(question);
        assertEquals("Active", question.getRecordStatusCd());
    }

    private WaQuestion emptyQuestion() {
        TextQuestionEntity q = new TextQuestionEntity();
        q.setId(321L);
        return q;
    }

}
