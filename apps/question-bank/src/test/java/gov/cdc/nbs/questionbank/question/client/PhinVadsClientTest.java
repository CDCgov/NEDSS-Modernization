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
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PhinVadsClientTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private PhinVadsClient phinVadsClient;



    @Test
    void should_return_valueset_by_oid_exist() throws URISyntaxException {
        final String url = "https://phinvads.cdc.gov/baseStu3/ValueSet/";
        ResponseEntity<PhinvadsValueSetByIDData> clientResp = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.getForEntity(url, PhinvadsValueSetByIDData.class)).thenReturn(clientResp);
        ResponseEntity<PhinvadsValueSetByIDData> cp = restTemplate.getForEntity(url, PhinvadsValueSetByIDData.class);
        assertNotNull(cp);
    }



    @Test
    void should_return_exception_when_oid_not_found() {
        String url = "https://phinvads.cdc.gov/baseStu3/ValueSet/";
        ResponseEntity<PhinvadsValueSetByIDData> clientResp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(url, PhinvadsValueSetByIDData.class)).thenReturn(clientResp);
        ResponseEntity<PhinvadsValueSetByIDData> cp = restTemplate.
                getForEntity(url, PhinvadsValueSetByIDData.class);
        assertEquals(404,  cp.getStatusCode().value());
    }


}