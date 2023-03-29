package gov.cdc.nbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.investigation.InvestigationResolver;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.repository.elasticsearch.InvestigationRepository;
import gov.cdc.nbs.support.EventMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class OpenInvestigationSteps {

    @Autowired
    private InvestigationRepository investigationRepository;

    @Autowired
    private ElasticsearchPersonRepository personRepository;

    @Autowired
    private InvestigationResolver investigationResolver;

    private Page<ElasticsearchPerson> personPage;
    private Page<Investigation> investigationResults;

    @Given("a patient has open investigations")
    public void a_patient_has_open_investigations() {
        setAvailablePatients();
        var patientId = personPage.getContent().get(0).getPersonUid();

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

    @Given("a patient does not have open investigations")
    public void a_patient_does_not_have_open_investigations() {
        setAvailablePatients();
        investigationRepository.deleteAll();
    }

    @When("I search for open investigations for a patient")
    public void i_search_for_open_investigations_for_a_patient() {
        var patientId = personPage.getContent().get(0).getPersonUid();
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

    private void setAvailablePatients() {
        personPage = personRepository.findAll(Pageable.ofSize(2));
    }
}
