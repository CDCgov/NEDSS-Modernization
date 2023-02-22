package gov.cdc.nbs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.EventController;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.support.EventMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DocumentsRequiringReviewSteps {
    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Autowired
    private LabReportRepository labReportRepository;

    @Autowired
    private EventController eventController;

    private Page<ElasticsearchPerson> personPage;
    private Page<LabReport> labReportResults;

    @Given("a patient has documents requiring review")
    public void a_patient_has_documents_requiring_review() {
        setAvailablePatients();
        var firstPerson = personPage.getContent().get(0);
        var report = EventMother.labReport_acidFastStain(firstPerson.getPersonUid());
        assertEquals("UNPROCESSED", report.getRecordStatusCd());
        var processedReport = EventMother.labReport_acidFastStain_complete(firstPerson.getPersonUid());
        assertEquals("PROCESSED", processedReport.getRecordStatusCd());
        labReportRepository.save(report);
        labReportRepository.save(processedReport);
    }

    @Given("a patient does not have documents requiring review")
    public void a_patient_does_not_have_documents_requiring_review() {
        setAvailablePatients();
        var patientId = personPage.getContent().get(0).getPersonUid();
        var reportsForPatient = eventController.findDocumentsRequiringReviewForPatient(patientId,
                new GraphQLPage(10, 0));
        labReportRepository.deleteAll(reportsForPatient.getContent());
    }

    @When("I search for documents requiring review for a patient")
    public void i_search_for_documents_requiring_review_for_a_patient() {
        setAvailablePatients();
        var patientId = personPage.getContent().get(0).getPersonUid();
        labReportResults = eventController.findDocumentsRequiringReviewForPatient(patientId, new GraphQLPage(10, 0));
    }

    @Then("I receive a list of documents requiring review")
    public void i_receive_a_list_of_documents_requiring_review() {
        assertNotNull(labReportResults);
        assertFalse(labReportResults.isEmpty());
        assertEquals(1, labReportResults.getTotalElements());
    }

    @Then("none are returned")
    public void none_are_returned() {
        assertNotNull(labReportResults);
        assertTrue(labReportResults.isEmpty());
    }

    private void setAvailablePatients() {
        var pageable = PageRequest.of(0, 2, Sort.by(ElasticsearchPerson.PERSON_UID));
        personPage = elasticsearchPersonRepository.findAll(pageable);
    }

}
