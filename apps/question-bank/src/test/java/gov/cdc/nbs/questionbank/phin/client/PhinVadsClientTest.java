package gov.cdc.nbs.questionbank.phin.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import gov.cdc.nbs.questionbank.question.response.PhinvadsValueSetByIDData;


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
        PhinvadsValueSetByIDData res = phinVadsClient.getValueSetByOID("2.16.840.1.114222.4.11.878");
        assertNotNull(res);
    }


    @Test
    void should_return_exception_when_oid_not_found() {

        try {
            String url = "https://phinvads.cdc.gov/baseStu3/ValueSet/";
            ReflectionTestUtils.setField(phinVadsClient, "valuesetByIDUrl", url, String.class);
            phinVadsClient.getValueSetByOID("2.16.840.1.114222.4.11.878.000");
        } catch (HttpClientErrorException e) {
            assertTrue(e instanceof HttpClientErrorException);
        }
    }


}
