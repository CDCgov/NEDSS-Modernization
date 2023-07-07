package gov.cdc.nbs.questionbank.phin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import gov.cdc.nbs.questionbank.phin.service.VocabSearchService;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResponse;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResults;

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
        ResponseEntity<ValueSetByOIDResponse> response =
                vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.878");
        assertEquals(valueSetByOIDResponse, response.getBody().getData());
    }

    @Test
    void should_return_exception_when_oid_not_found() {
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000"))
                .thenThrow(ResponseStatusException.class);
        ResponseEntity<ValueSetByOIDResponse> response =
                vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000");
        assertEquals("404", response.getBody().getStatus().getCode());
    }

    @Test
    void should_return_exception_when_internal_fails() {
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000"))
                .thenThrow(NullPointerException.class);
        ResponseEntity<ValueSetByOIDResponse> response =
                vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000");
        assertEquals("500", response.getBody().getStatus().getCode());
    }

    @Test
    void test_expected_exception() {
        when(vocabSearchService.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000")).thenReturn(null);
        var result =  vocabSearchController.fetchValueSetInfoByOID("2.16.840.1.114222.4.11.000").getBody().getData();
        Exception exception = assertThrows(RuntimeException.class, () -> {
                    result.getValueSetCode();
        });
        assertTrue(exception instanceof NullPointerException);
    }

    @Test
    void test_expected_for_invalid_pattern_oid() {
        try {
            vocabSearchController.fetchValueSetInfoByOID("OID");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

}
