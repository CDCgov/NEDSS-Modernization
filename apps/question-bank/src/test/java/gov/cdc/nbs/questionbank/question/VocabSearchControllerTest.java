package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResponse;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResults;
import gov.cdc.nbs.questionbank.question.service.VocabSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VocabSearchControllerTest {
    @Mock
    VocabSearchService vocabSearchService;

    @InjectMocks
    private VocabSearchController vocabSearchController;

    @Test
    void should_return_valueset_by_oid_exist() {
        ValueSetByOIDResults valueSetByOIDResponse = new ValueSetByOIDResults();
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.878")).thenReturn(valueSetByOIDResponse);
        ResponseEntity<ValueSetByOIDResponse> response = vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.878");
        assertEquals(valueSetByOIDResponse, response.getBody().getData());
    }

    @Test
    void should_return_exception_when_oid_not_found() {
        ValueSetByOIDResults valueSetByOIDResponse = new ValueSetByOIDResults();
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000")).thenThrow(ResponseStatusException.class);
        ResponseEntity<ValueSetByOIDResponse> response = vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000");
        assertEquals("404", response.getBody().getStatus().getCode());
    }

    @Test
    void should_return_exception_when_internal_fails() {
        ValueSetByOIDResults valueSetByOIDResponse = new ValueSetByOIDResults();
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000")).thenThrow(NullPointerException.class);
        ResponseEntity<ValueSetByOIDResponse> response = vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000");
        assertEquals("500", response.getBody().getStatus().getCode());
    }

    @Test
    void test_expected_exception() {
        ValueSetByOIDResults valueSetByOIDResponse = new ValueSetByOIDResults();
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000")).thenReturn(null);
        Exception exception =assertThrows(RuntimeException.class, ()-> {
            vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000").getBody().getData().getValueSetCode();
                });
        assertTrue(exception instanceof  NullPointerException);

    }

}
