package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;

@ExtendWith(MockitoExtension.class)
class QuestionFinderTest {

    @Mock
    private WaQuestionRepository repository;

    @Mock
    private QuestionMapper mapper;

    @InjectMocks
    private QuestionFinder finder;

    @Test
    void should_try_id_search() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        when(repository.findAllByNameOrIdentifier(eq("1234"), captor.capture(), Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // given a search request that is an id
        FindQuestionRequest request = new FindQuestionRequest("1234");

        // when a search is performed
        finder.find(request, PageRequest.ofSize(10));

        // then the converted Id is used
        Long actual = captor.getValue();
        assertEquals(1234L, actual.longValue());
    }

    @Test
    void should_not_fail_search_if_not_id() {
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        when(repository.findAllByNameOrIdentifier(eq("search"), captor.capture(), Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // given a search request that is an id
        FindQuestionRequest request = new FindQuestionRequest("search");

        // when a search is performed
        finder.find(request, PageRequest.ofSize(10));

        // then the converted Id is used
        Long actual = captor.getValue();
        assertEquals(-1L, actual.longValue());
    }

}
