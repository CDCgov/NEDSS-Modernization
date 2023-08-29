package gov.cdc.nbs.questionbank.valueset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest.StatusCode;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddConceptSteps {

    @Autowired
    private ValueSetController controller;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private ValueSetMother valueSetMother;

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;

    private Instant now;


    @When("I send a request to add a concept to a value set")
    public void i_send_a_request_to_add_a_concept() {
        now = Instant.now();
        String codesetName = valueSetMother.one().getId().getCodeSetNm();
        AddConceptRequest request = new AddConceptRequest(
                "TestCode",
                "TestDisplayName",
                "TestShortDisplay",
                now,
                now,
                StatusCode.A,
                "TestAdminComment",
                new AddConceptRequest.MessagingInfo(
                        "TestConceptCode",
                        "TestConceptName",
                        "TestPreferredConceptName",
                        "ABNORMAL_FLAGS_HL7"));
        try {
            controller.addConcept(codesetName, request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the concept is added to the value set")
    public void the_concept_is_added() {
        List<CodeValueGeneral> concepts =
                codeValueGeneralRepository.findByIdCodeSetNm(valueSetMother.one().getId().getCodeSetNm(), null);
        CodeValueGeneral codeSystem = codeValueGeneralRepository
                .findByIdCodeSetNmAndIdCode("CODE_SYSTEM", "ABNORMAL_FLAGS_HL7")
                .orElseThrow();
        CodeValueGeneral newConcept = concepts.stream()
                .filter(f -> f.getId().getCode().equals("TestCode"))
                .findFirst()
                .orElseThrow();
        assertEquals("TestDisplayName", newConcept.getCodeDescTxt());
        assertEquals("TestShortDisplay", newConcept.getCodeShortDescTxt());
        assertEquals("TestShortDisplay", newConcept.getCodeShortDescTxt());
        assertEquals('A', newConcept.getStatusCd().charValue());
        assertEquals("Active", newConcept.getConceptStatusCd());
        assertEquals("TestAdminComment", newConcept.getAdminComments());
        assertEquals("TestConceptCode", newConcept.getConceptCode());
        assertEquals("TestConceptName", newConcept.getConceptNm());
        assertEquals("TestPreferredConceptName", newConcept.getConceptPreferredNm());
        assertEquals(codeSystem.getCodeShortDescTxt(), newConcept.getCodeSystemDescTxt());
        assertEquals(codeSystem.getCodeDescTxt(), newConcept.getCodeSystemCd());
        assertEquals("PHIN", newConcept.getConceptTypeCd());
        assertNotNull(newConcept.getConceptStatusTime());
        assertNotNull(newConcept.getAddTime());
        assertNotNull(newConcept.getAddUserId());
    }
}
