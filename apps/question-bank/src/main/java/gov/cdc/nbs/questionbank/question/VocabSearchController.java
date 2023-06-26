package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.question.response.Status;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResponse;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResults;
import gov.cdc.nbs.questionbank.question.service.VocabSearchService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("search")
public class VocabSearchController {

    @Autowired
    VocabSearchService vocabSearchService;

    @GetMapping("/oid/{oid}")
    ResponseEntity<ValueSetByOIDResponse> fetchValueSetInfoByOID(@PathVariable("oid") @NonNull String OID) {
        ValueSetByOIDResponse response = null;
        Status status = null;

        try {
            ValueSetByOIDResults data = vocabSearchService.fetchValueSetInfoByOID(OID);
            status = Status.builder().code("200").type("SUCCESS").message("OID_DATA_FOUND")
                    .description("Fetch Valueset data by OID successfully").build();
            response = ValueSetByOIDResponse.builder().data(data).status(status).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException rse) {
            status = Status.builder().code("404").type("FAILURE").message("OID_NOT_FOUND")
                    .description(rse.getLocalizedMessage()).build();
            response = ValueSetByOIDResponse.builder().status(status).build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            status = Status.builder().code("400").type("FAILURE").message("INTERNAL_SERVER_ERROR")
                    .description(e.getLocalizedMessage()).build();
            response = ValueSetByOIDResponse.builder().status(status).build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}



