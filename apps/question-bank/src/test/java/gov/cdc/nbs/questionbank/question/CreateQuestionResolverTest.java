package gov.cdc.nbs.questionbank.question;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;
import gov.cdc.nbs.questionbank.support.QuestionDataMother;

@ExtendWith(MockitoExtension.class)
class CreateQuestionResolverTest {

    @Mock
    private QuestionCreator creator;

    @Mock
    private EntityMapper entityMapper;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @InjectMocks
    private CreateQuestionResolver resolver;

    @Test
    void should_send_create_text_request() {
        TextQuestionEntity mockEntity = Mockito.mock(TextQuestionEntity.class);
        when(creator.create(Mockito.any(QuestionRequest.CreateTextQuestion.class), eq(1234L))).thenReturn(mockEntity);

        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = QuestionDataMother.textQuestionData();
        resolver.createTextQuestion(data);

        // then the creator is called
        verify(creator, times(1)).create(Mockito.any(QuestionRequest.CreateTextQuestion.class), eq(1234L));
        verify(entityMapper, times(1)).toTextQuestion(Mockito.any(TextQuestionEntity.class));
    }

    @Test
    void should_send_create_date_request() {
        DateQuestionEntity mockEntity = Mockito.mock(DateQuestionEntity.class);
        when(creator.create(Mockito.any(QuestionRequest.CreateDateQuestion.class), eq(1234L))).thenReturn(mockEntity);

        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = QuestionDataMother.dateQuestionData();
        resolver.createDateQuestion(data);

        // then the creator is called
        verify(creator, times(1)).create(Mockito.any(QuestionRequest.CreateDateQuestion.class), eq(1234L));
        verify(entityMapper, times(1)).toDateQuestion(Mockito.any(DateQuestionEntity.class));
    }

    @Test
    void should_send_create_numeric_request() {
        NumericQuestionEntity mockEntity = Mockito.mock(NumericQuestionEntity.class);
        when(creator.create(Mockito.any(QuestionRequest.CreateNumericQuestion.class), eq(1234L)))
                .thenReturn(mockEntity);

        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = QuestionDataMother.numericQuestionData();
        resolver.createNumericQuestion(data);

        // then the creator is called
        verify(creator, times(1)).create(Mockito.any(QuestionRequest.CreateNumericQuestion.class), eq(1234L));
        verify(entityMapper, times(1)).toNumericQuestion(Mockito.any(NumericQuestionEntity.class));
    }

    @Test
    void should_send_create_dropdown_request() {
        DropdownQuestionEntity mockEntity = Mockito.mock(DropdownQuestionEntity.class);
        when(creator.create(Mockito.any(QuestionRequest.CreateDropdownQuestion.class), eq(1234L)))
                .thenReturn(mockEntity);

        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = QuestionDataMother.dropdownQuestionData();
        resolver.createDropdownQuestion(data);

        // then the creator is called
        verify(creator, times(1)).create(Mockito.any(QuestionRequest.CreateDropdownQuestion.class), eq(1234L));
        verify(entityMapper, times(1)).toDropdownQuestion(Mockito.any(DropdownQuestionEntity.class));
    }

    private NbsUserDetails userDetails() {
        return new NbsUserDetails(1234L,
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                null,
                null,
                true);
    }

}
