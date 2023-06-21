package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
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
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@ExtendWith(MockitoExtension.class)
class QuestionUpdaterTest {

    @Mock
    private WaQuestionRepository repository;

    @Mock
    private WaQuestionHistRepository histRepository;

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
    void should_increment_version() {
        // given an active question and a working repository
        when(repository.findById(321L)).thenReturn(Optional.of(emptyQuestion()));
        ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
        when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

        // when a set status to inactive request is processed
        updater.setStatus(9L, 321L, false);

        // then the question should have incremented version control number
        WaQuestion question = captor.getValue();
        assertNotNull(question);
        assertEquals(2, question.getVersionCtrlNbr().intValue());
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

    @Test
    void should_throw_not_found() {
        // given no question exists for id

        // when a set status request is processed, then a QuestionNotFoundException should be thrown
        assertThrows(QuestionNotFoundException.class, () -> updater.setStatus(1L, 222L, false));
    }

    private WaQuestion emptyQuestion() {
        TextQuestionEntity q = new TextQuestionEntity();
        q.setId(321L);
        q.setVersionCtrlNbr(1);
        return q;
    }

}
