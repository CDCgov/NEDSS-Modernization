package gov.cdc.nbs.questionbank.valueset.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.exception.NotFoundException;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.questionbank.valueset.ValueSetController;
import gov.cdc.nbs.questionbank.valueset.exception.ConceptNotFoundException;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateConceptSteps {

    @Autowired
    private ValueSetMother valueSetMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private ValueSetController controller;

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;

    private Instant updatedTime;


    @Given("a value set exists")
    public void a_value_set_exists() {
        valueSetMother.clean();
        valueSetMother.valueSet();
    }

    @When("I send an update concept request")
    public void i_send_an_update_concept_request() {
        CodesetId id = valueSetMother.one().getId();
        CodeValueGeneral concept = valueSetMother.concept(id.getCodeSetNm()).get(0);
        updatedTime = Instant.now().minus(5, ChronoUnit.DAYS);
        UpdateConceptRequest request = new UpdateConceptRequest(
                "updated name",
                "updated display",
                updatedTime,
                updatedTime,
                false,
                "New admin comments",
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "message Code",
                        "message name",
                        "preferred name",
                        "HEALTH_LEVEL_7"));
        try {
            controller.updateConcept(id.getCodeSetNm(), concept.getId().getCode(), request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send an update concept request for a concept that doesn't exist")
    public void i_send_update_for_concept_that_doesnt_exist() {
        UpdateConceptRequest request = new UpdateConceptRequest(
                "updated name",
                "updated display",
                updatedTime,
                updatedTime,
                false,
                "New admin comments", null);
        try {
            controller.updateConcept("I dont", "exist", request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (ConceptNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the concept is updated")
    public void the_concept_is_updated() {
        CodesetId id = valueSetMother.one().getId();
        CodeValueGeneral concept = valueSetMother.concept(id.getCodeSetNm()).get(0);
        assertNull(exceptionHolder.getException());
        CodeValueGeneral actual = codeValueGeneralRepository
                .findByIdCodeSetNmAndIdCode(id.getCodeSetNm(), concept.getId().getCode())
                .orElseThrow();
        assertEquals("updated name", actual.getCodeDescTxt());
        assertEquals("updated display", actual.getCodeShortDescTxt());
        assertEquals(0, Duration.between(updatedTime, actual.getEffectiveFromTime()).getSeconds()); // DB loses accuracy to nano's
        assertEquals(0, Duration.between(updatedTime, actual.getEffectiveToTime()).getSeconds());
        assertEquals('I', actual.getStatusCd().charValue());
        assertTrue(concept.getStatusTime().isBefore(actual.getStatusTime()));
        assertEquals("New admin comments", actual.getAdminComments());

        // messaging info
        assertEquals("message Code", actual.getConceptCode());
        assertEquals("message name", actual.getConceptNm());
        assertEquals("preferred name", actual.getConceptPreferredNm());
        assertEquals("Health Level Seven", actual.getCodeSystemDescTxt()); // From test db
        assertEquals("2.16.840.1.113883", actual.getCodeSystemCd()); // From test db
    }

    @Then("a not found exception is thrown")
    public void a_not_found_exception_is_thrown() {
        assertTrue(exceptionHolder.getException() instanceof NotFoundException);
    }
}
