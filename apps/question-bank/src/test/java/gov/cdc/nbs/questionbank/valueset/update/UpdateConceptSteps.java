package gov.cdc.nbs.questionbank.valueset.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.exception.NotFoundException;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.questionbank.valueset.ConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateConceptSteps {

    @Autowired
    private ValueSetMother valueSetMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;

    @Autowired
    private ConceptRequest request;
    
    @Autowired
    private ObjectMapper mapper;

    private Instant updatedTime;

    private final Active<ResultActions> conceptResponse = new Active<>();
    private final Active<UpdateConceptRequest> conceptRequestBody = new Active<>();


    @Given("a value set exists")
    public void a_value_set_exists() {
        valueSetMother.clean();
        valueSetMother.valueSet();
    }

    @Given("I create an update concept request")
    public void i_create_an_update_concept_request() {
        updatedTime = Instant.parse("2023-01-01T00:00:00Z");
        UpdateConceptRequest request = new UpdateConceptRequest(
                "updated name",
                "updated display",
                updatedTime,
                updatedTime,
                true,
                "New admin comments",
                new UpdateConceptRequest.ConceptMessagingInfo(
                        "message Code",
                        "message name",
                        "preferred name",
                        "HEALTH_LEVEL_7"));

        conceptRequestBody.active(request);
    }

    @When("I send an update concept request")
    public void i_send_an_update_concept_request() {
        CodesetId id = valueSetMother.one().getId();
        CodeValueGeneral concept = valueSetMother.concept(id.getCodeSetNm()).get(0);
        
        try {
            conceptResponse.active(request.updateConceptRequest(id.getCodeSetNm(), concept.getId().getCode(), conceptRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send an update concept request for a concept that doesn't exist")
    public void i_send_update_for_concept_that_doesnt_exist() {
        try {
            conceptResponse.active(request.updateConceptRequest("I dont", "exist", conceptRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the concept is updated")
    public void the_concept_is_updated() throws Exception{
        assertNull(exceptionHolder.getException());

        String res = this.conceptResponse.active().andReturn().getResponse().getContentAsString();
        Concept response = mapper.readValue(res, Concept.class);

        assertEquals("updated name", response.longName());
        assertEquals("updated display", response.display());
        assertThat(response.effectiveFromTime()).isEqualTo("2023-01-01T00:00:00Z");
        assertThat(response.effectiveToTime()).isEqualTo("2023-01-01T00:00:00Z");
        assertEquals("Active", response.status());

        // messaging info
        assertEquals("message Code", response.conceptCode());
        assertEquals("preferred name", response.messagingConceptName());
        assertEquals("Health Level Seven", response.codeSystem()); // From test db
    }

    @Then("a not found exception is thrown")
    public void a_not_found_exception_is_thrown() {
        assertTrue(exceptionHolder.getException() instanceof NotFoundException);
    }
}
