package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.event.document.CaseReportIdentifier;
import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileDocumentsRequiringReviewSteps {

  private final Active<CaseReportIdentifier> caseReport;
  private final Active<MorbidityReportIdentifier> morbidityReport;
  private final Active<LabReportIdentifier> laboratoryReport;
  private final Active<PatientIdentifier> patient;
  private final PatientFileDocumentsRequiringReviewRequester requester;
  private final Active<ResultActions> response;

  PatientFileDocumentsRequiringReviewSteps(
      final Active<CaseReportIdentifier> caseReport,
      final Active<MorbidityReportIdentifier> morbidityReport,
      final Active<LabReportIdentifier> laboratoryReport,
      final Active<PatientIdentifier> patient,
      final PatientFileDocumentsRequiringReviewRequester requester,
      final Active<ResultActions> response
  ) {
    this.caseReport = caseReport;
    this.morbidityReport = morbidityReport;
    this.laboratoryReport = laboratoryReport;
    this.patient = patient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the documents requiring review for the patient")
  public void i_view_the_documents_requiring_review_for_the_patient() {
    this.patient.maybeActive().map(this.requester::request).ifPresent(this.response::active);
  }

  @Then("the patient file has no {documentType}(s) requiring review")
  public void no_documents_are_requiring_review(final String documentType) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[?(@.type=='%s')]", documentType).doesNotExist());
  }

  @Then("there is one {documentType} requiring review")
  public void count(final String type) throws Exception {
    count(type, 1);
  }

  @Then("there {int} {documentType}(s) requiring review")
  public void count(final String type, final int count) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[?(@.type=='%s')]", type).value(hasSize(count)));
  }

  @Then("the patient file has the {documentType} requiring review")
  public void the_correct_document_requiring_review_is_returned(final String documentType) throws Exception {
    String local = switch (documentType) {
      case "Case Report" -> this.caseReport.active().local();
      case "Morbidity Report" -> this.morbidityReport.active().local();
      case "Laboratory Report" -> this.laboratoryReport.active().local();
      default -> null;
    };
    this.response.active()
        .andExpect(jsonPath("$.[?(@.type=='%s')].[?(@.local=='%s')]", documentType, local).exists());
  }

  @Then("the {documentType} requiring review is electronic")
  public void the_patient_document_requiring_review_is_electronic(final String documentType)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].isElectronic",
                documentType
            )
                .value(hasItem(equalTo(true)))
        );
  }

  @Then("the {documentType} requiring review is not electronic")
  public void the_patient_document_requiring_review_is_not_electronic(final String documentType)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].isElectronic",
                documentType
            )
                .value(hasItem(equalTo(false)))
        );
  }

  @Then("the {documentType} requiring review was sent by (the ){string}")
  public void the_patient_document_requiring_review_was_sent_by(
      final String documentType,
      final String facility
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].sendingFacility",
                documentType
            )
                .value(hasItem(equalTo(facility)))
        );
  }

  @Then("the {documentType} requiring review has the condition {string}")
  public void the_patient_document_requiring_review_has_the_condition(
      final String documentType,
      final String title
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].[?(@.condition=='%s')]",
                documentType, title
            )
                .exists()
        );
  }

  @Then("the {documentType} requiring review was received on {localDate} at {time}")
  public void receivedOn(
      final String documentType,
      final LocalDate on,
      final LocalTime at
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].[?(@.dateReceived=='%s')]",
                documentType,
                LocalDateTime.of(on, at)
            )
                .exists()
        );
  }

  @Then("the {documentType} requiring review has the event date {localDate}")
  public void eventDate(
      final String documentType,
      final LocalDate on
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].[?(@.eventDate=='%s')]",
                documentType,
                on
            ).exists()
        );
  }

  @Then("the {documentType} requiring review was reported by {string}")
  public void reportedBy(final String type, final String value)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s' && @.reportingFacility=='%s')]",
                type, value
            ).exists()
        );
  }

  @Then("the {documentType} requiring review was ordered by the {string} facility")
  public void orderedBy(final String type, final String value)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s' && @.orderingFacility=='%s')]",
                type, value
            ).exists()
        );
  }

  @Then("the {documentType} requiring review was ordered by {string} {string} {string}")
  public void orderedBy(
      final String type,
      final String prefix,
      final String first,
      final String last
  )
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type=='%s')].orderingProvider.[?(@.prefix=='%s' && @.first=='%s' && @.last=='%s')]",
                type,
                prefix,
                first,
                last
            ).exists());
  }

  @Then("the {documentType} requiring review contains the {string} treatment")
  public void hasTreatment(final String type, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.type=='%s')].treatments[?(@=='%s')]", type, value).exists()
        );
  }

  @Then("the {documentType} requiring review does not contain treatments")
  public void noTreatments(final String type) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.type=='%s')].treatments]", type).isEmpty()
        );
  }


  @Then("the {documentType} requiring review contains a(n) {string} test with the result {string}")
  public void hasNumericResultedTest(final String type, final String test, final String result) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.type=='%s')].resultedTests[?(@.name=='%s')].result", type, test)
                .value(hasItem(containsStringIgnoringCase(result)))
        );
  }

  @Then("the {documentType} requiring review does not contain resulted tests")
  public void noResultedTests(final String type) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.type=='%s')].resultedTest]", type).isEmpty()
        );
  }

  @Then("the laboratory report requiring review contains specimen source of {specimenSource}")
  public void hasSpecimenSource(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.type=='%s')].specimen.source")
                .value(hasItem(containsStringIgnoringCase(value)))
        );
  }

  @Then("the {documentType} requiring review contains specimen site of {specimenSite}")
  public void hasSpecimenSite(String type, String site) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.type=='%s')].specimen.site", type)
                .value(hasItem(containsStringIgnoringCase(site)))
        );
  }
}
