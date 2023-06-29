package gov.cdc.nbs.questionbank.phin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import gov.cdc.nbs.questionbank.phin.service.VocabSearchService;
import gov.cdc.nbs.questionbank.question.response.Status;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResponse;
import gov.cdc.nbs.questionbank.question.response.ValueSetByOIDResults;
import lombok.NonNull;

@RestController
@RequestMapping("/api/v1/phin")
public class VocabSearchController {

    @Autowired
    VocabSearchService vocabSearchService;

    @GetMapping("/oid/{oid}")
    ResponseEntity<ValueSetByOIDResponse> fetchValueSetInfoByOID(@PathVariable("oid") @NonNull String oid) {
        ValueSetByOIDResponse response = null;
        Status status = null;
        if (isValidInput(oid)) {
            try {
                ValueSetByOIDResults data = vocabSearchService.fetchValueSetInfoByOID(oid);
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
                status = Status.builder().code("500").type("FAILURE").message("INTERNAL_SERVER_ERROR")
                        .description(e.getLocalizedMessage()).build();
                response = ValueSetByOIDResponse.builder().status(status).build();
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        status = Status.builder().code("400").type("FAILURE").message("INVALID_INPUT")
                .description("Invalid input").build();
        response = ValueSetByOIDResponse.builder().status(status).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private boolean isValidInput(String oid) {
        oid = oid.replace(".", "");
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(oid);
        return matcher.matches();
    }


}


