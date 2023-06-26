package gov.cdc.nbs.questionbank.question.client;

import gov.cdc.nbs.questionbank.question.VocabSearchController;
import gov.cdc.nbs.questionbank.question.response.PhinvadsValueSetByIDData;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResponse;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResults;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhinVadsClientTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private PhinVadsClient phinVadsClient;

    /*@Test
    void should_return_valueset_by_oid_exist() {
        String url = "http://phinvads.cdc.gov/baseStu3/ValueSet/2.16.840.1.114222.4.11.878";
        ResponseEntity<PhinvadsValueSetByIDData> clientResp = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.getForEntity(ArgumentMatchers.anyString(),ArgumentMatchers.any(PhinvadsValueSetByIDData.class))).thenReturn(clientResp);
        PhinvadsValueSetByIDData response = phinVadsClient.getValueSetByOID("2.16.840.1.114222.4.11.878");
        assertEquals(clientResp.getBody(), response);
    }

    @Test
    void should_return_exception_when_oid_not_found() {
        String url = "https://phinvads.cdc.gov/baseStu3/ValueSet/2.16.840.1.114222.4.11.000";
        ResponseEntity<PhinvadsValueSetByIDData> clientResp = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.getForEntity(url, PhinvadsValueSetByIDData.class)).thenReturn(clientResp);
        PhinvadsValueSetByIDData response = phinVadsClient.getValueSetByOID("2.16.840.1.114222.4.11.000");
        assertEquals("404", response);
    }*/

}