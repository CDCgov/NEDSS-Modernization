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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


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
        ReflectionTestUtils.setField(phinVadsClient, "valuesetByIDUrl", url, String.class);
        ResponseEntity<PhinvadsValueSetByIDData> clientResp = new ResponseEntity<>(HttpStatus.OK);
        PhinvadsValueSetByIDData res = phinVadsClient.getValueSetByOID("2.16.840.1.114222.4.11.878");
         assertNotNull(res);
    }


    @Test
    void should_return_exception_when_oid_not_found() {

        try {
            String url = "https://phinvads.cdc.gov/baseStu3/ValueSet/";
            ReflectionTestUtils.setField(phinVadsClient, "valuesetByIDUrl", url, String.class);
            PhinvadsValueSetByIDData res = phinVadsClient.getValueSetByOID("2.16.840.1.114222.4.11.878.000");
        }catch (HttpClientErrorException e){
            assertTrue(e instanceof  HttpClientErrorException);
        }
    }


}