package gov.cdc.nbs.patient.documentsrequiringreview;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.document.DocumentMother;
import gov.cdc.nbs.patient.document.TestDocuments;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.morbidity.MorbidityReportMother;
import gov.cdc.nbs.patient.morbidity.TestMorbidityReports;
import gov.cdc.nbs.patient.profile.report.lab.LabReportMother;
import gov.cdc.nbs.patient.profile.report.lab.TestLabReports;
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

    @Autowired
    private MorbidityReportMother morbidityReportMother;

    @Autowired
    private DocumentMother documentMother;

    @Autowired
    private TestLabReports labReports;

    @Autowired
    private TestMorbidityReports morbReports;

    @Autowired
    private TestDocuments documents;


    private Exception e;

    @Before
    public void setup() {
        labReportMother.reset();
        holder.reset();
        e = null;
    }

    @Given("the patient has an unprocessed {string}")
    public void patient_has_an_unprocessed_document(String documentType) {
        long piedmontHospital = 10003001L; // From test db
        PatientIdentifier revision = patientMother.revise(patients.one());
        switch (documentType) {
            case "lab report":
                labReportMother.unprocessed(revision.id(), piedmontHospital);
                break;
            case "morbidity report":
                morbidityReportMother.unprocessed(revision.id(), piedmontHospital);
                break;
            case "document":
                documentMother.unprocessed(revision.id());
                break;
            default:
                throw new IllegalArgumentException("Unrecognized parameter: " + documentType);
        }
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

    @Then("the {string} requiring review is returned")
    public void the_correct_document_requiring_review_is_returned(String documentType) {
        switch (documentType) {
            case "lab report":
                Long labReport = labReports.one();
                assertTrue(holder.all().anyMatch(d -> d.id().equals(labReport)));
                break;
            case "morbidity report":
                Long morbidityReport = morbReports.one();
                assertTrue(holder.all().anyMatch(d -> d.id().equals(morbidityReport)));
                break;
            case "document":
                Long document = documents.one();
                assertTrue(holder.all().anyMatch(d -> d.id().equals(document)));
                break;
            default:
                throw new IllegalArgumentException("Unrecognized parameter: " + documentType);
        }
    }

    @Then("no {string} are returned")
    public void no_lab_reports_are_returned(String documentType) {
        switch (documentType) {
            case "lab report":
                holder.all().forEach(d -> assertNotEquals("LabReport", d.type()));
                break;
            case "morbidity report":
                holder.all().forEach(d -> assertNotEquals("MorbReport", d.type()));
                break;
            case "document":
                holder.all().forEach(d -> assertNotEquals("Document", d.type()));
                break;
            default:
                throw new IllegalArgumentException("Unrecognized parameter: " + documentType);
        }
    }

    @Then("an access denied exception is thrown")
    public void an_exception_is_thrown() {
        assertNotNull(e);
        assertTrue(e instanceof AccessDeniedException);
    }

    @Then("a credentials not found exception is thrown")
    public void an_credentials_not_found_exception_is_thrown() {
        assertNotNull(e);
        assertTrue(e instanceof AuthenticationCredentialsNotFoundException);
    }

}
