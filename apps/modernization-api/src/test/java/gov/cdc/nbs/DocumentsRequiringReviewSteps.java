package gov.cdc.nbs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.labreport.LabReportFinder;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.support.EventMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DocumentsRequiringReviewSteps {
    @Autowired
    private LabReportRepository labReportRepository;

    @Autowired
    private LabReportFinder labReportFinder;

    private Page<LabReport> labReportResults;
    private final long patientId = 12345L;

    @Given("a patient has documents requiring review")
    public void a_patient_has_documents_requiring_review() {
        var report = EventMother.labReport_acidFastStain(patientId);
        assertEquals("UNPROCESSED", report.getRecordStatusCd());
        labReportRepository.save(report);

        var processedReport = EventMother.labReport_acidFastStain_complete(patientId);
        assertEquals("PROCESSED", processedReport.getRecordStatusCd());
        labReportRepository.save(processedReport);
    }

    @Given("a patient does not have documents requiring review")
    public void a_patient_does_not_have_documents_requiring_review() {
        labReportRepository.deleteAll();
    }

    @When("I search for documents requiring review for a patient")
    public void i_search_for_documents_requiring_review_for_a_patient() {
        labReportResults = labReportFinder.findUnprocessedDocumentsForPatient(patientId, Pageable.ofSize(100));
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

}
