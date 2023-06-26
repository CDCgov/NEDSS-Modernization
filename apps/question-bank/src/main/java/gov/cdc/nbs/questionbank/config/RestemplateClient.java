package gov.cdc.nbs.questionbank.config;

import gov.cdc.nbs.questionbank.question.response.PhinvadsValueSetByIDData;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Configuration
public class RestemplateClient {

    public RestTemplate getRestTemplate(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_NDJSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PhinvadsValueSetByIDData> entity = new HttpEntity<>(headers);

//        RestTemplate template = new RestTemplate(entity);

return null;
    }


}
