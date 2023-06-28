package gov.cdc.nbs.questionbank.question.client;

import gov.cdc.nbs.questionbank.question.response.ValueSetApiResponse;
import gov.cdc.nbs.questionbank.question.response.PhinvadsValueSetByIDData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class PhinVadsClient {

    @Value("${valueset.byidurl}")
    private String valuesetByIDUrl;

    public PhinvadsValueSetByIDData getValueSetByOID(String oid) throws  ResponseStatusException{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PhinvadsValueSetByIDData> clientResp = restTemplate.getForEntity(valuesetByIDUrl + oid,
                PhinvadsValueSetByIDData.class);
        if (clientResp.getStatusCode() == HttpStatus.NOT_FOUND) {
            log.info("Phinvads api calling status", clientResp.getBody().getDescription());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, clientResp.getBody().getDescription());
        }
        return clientResp.getBody();
    }

}
