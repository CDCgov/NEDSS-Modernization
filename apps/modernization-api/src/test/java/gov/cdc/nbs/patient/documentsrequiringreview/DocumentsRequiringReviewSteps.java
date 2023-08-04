package gov.cdc.nbs.patient.documentsrequiringreview;

import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.report.lab.LabReportMother;
import gov.cdc.nbs.support.TestAvailable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DocumentsRequiringReviewSteps {

    @Autowired
    private DocumentRequiringReviewHolder holder;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    private DocumentsRequiringReviewResolver resolver;

    @Autowired
    private PatientMother patientMother;

    @Autowired
    private LabReportMother labReportMother;

    private Exception e;

    @Before
    public void setup() {
        labReportMother.reset();
        holder.reset();
        e = null;
    }

    @Given("the patient has an unprocessed lab report")
    public void patient_has_an_unprocessed_lab_report() {
        PatientIdentifier revision = patientMother.revise(patients.one());
        labReportMother.unprocessedLabReport(revision.id());
    }

    @When("I search for documents requiring review for the patient")
    public void i_search_for_documents_req_review() {
        Long patient = patients.one().id();
        try {
            Page<DocumentRequiringReview> docs = resolver.findDocumentsRequiringReviewForPatient(patient, null);
            docs.get().forEach(holder::available);
        } catch (Exception ex) {
            e = ex;
        }
    }

    @Then("the lab report requiring review is returned")
    public void the_lab_report_requiring_review_is_returned() {
        DocumentRequiringReview doc = holder.one();
        assertNotNull(doc);
    }

    @Then("an access denied is thrown")
    public void an_exception_is_thrown() {
        assertNotNull(e);
    }

}
