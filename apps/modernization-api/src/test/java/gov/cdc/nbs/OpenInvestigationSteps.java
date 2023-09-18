package gov.cdc.nbs;

import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.event.search.investigation.InvestigationResolver;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.elasticsearch.InvestigationRepository;
import gov.cdc.nbs.support.EventMother;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
public class OpenInvestigationSteps {

    @Autowired
    InvestigationRepository investigationRepository;

    @Autowired
    InvestigationResolver investigationResolver;

    @Autowired
    TestActive<PatientIdentifier> patient;

    private Page<Investigation> investigationResults;

    @Before("@open_investigations")
    public void reset() {
        investigationRepository.deleteAll();
    }

    @Given("a patient has open investigations")
    public void a_patient_has_open_investigations() {

        var patientId = patient.active().id();

        var investigation1 = EventMother.investigation_bacterialVaginosis(patientId);
        assertEquals("O", investigation1.getInvestigationStatusCd());
        assertEquals("STD", investigation1.getProgAreaCd());
        var investigation2 = EventMother.investigation_trichomoniasis(patientId);
        assertEquals("O", investigation2.getInvestigationStatusCd());
        assertEquals("ARBO", investigation2.getProgAreaCd());
        var investigation3 = EventMother.investigation_trichomoniasis_closed(patientId);
        assertEquals("C", investigation3.getInvestigationStatusCd());
        assertEquals("STD", investigation3.getProgAreaCd());

        investigationRepository.saveAll(Arrays.asList(investigation1, investigation2, investigation3));
    }

    @When("I search for open investigations for a patient")
    public void i_search_for_open_investigations_for_a_patient() {
        var patientId = patient.active().id();
        investigationResults = investigationResolver.findOpenInvestigationsForPatient(patientId, new GraphQLPage(10));
    }

    @Then("I receive a list of open investigations")
    public void i_receive_a_list_of_open_investigations() {
        assertNotNull(investigationResults);
        assertFalse(investigationResults.isEmpty());
        assertEquals(1, investigationResults.getTotalElements());
    }

    @Then("no open investigations are returned")
    public void no_open_investigations_are_returned() {
        assertNotNull(investigationResults);
        assertTrue(investigationResults.isEmpty());
    }

}
