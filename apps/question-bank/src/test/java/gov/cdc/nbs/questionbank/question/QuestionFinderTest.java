package gov.cdc.nbs.questionbank.question;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@ExtendWith(MockitoExtension.class)
class QuestionFinderTest {

    @Mock
    private WaQuestionRepository repository;

    @Mock
    private QuestionMapper mapper;

    @InjectMocks
    private QuestionFinder finder;



}
