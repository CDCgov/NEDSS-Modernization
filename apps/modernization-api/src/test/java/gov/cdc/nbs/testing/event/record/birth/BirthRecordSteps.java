package gov.cdc.nbs.testing.event.record.birth;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BirthRecordSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<BirthRecordIdentifier> activeRecord;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final BirthRecordMother mother;

  BirthRecordSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<BirthRecordIdentifier> activeRecord,
      final Active<InvestigationIdentifier> activeInvestigation,
      final BirthRecordMother mother) {
    this.activePatient = activePatient;
    this.activeRecord = activeRecord;
    this.activeInvestigation = activeInvestigation;
    this.mother = mother;
  }

  @Given("the patient has a birth record")
  public void has() {
    has(RandomUtil.getRandomNumericString(50));
  }

  @Given("the patient has the {string} birth record")
  public void has(final String certificate) {
    activePatient.maybeActive().ifPresent(patient -> mother.create(patient, certificate));
  }

  @When("the birth record was received on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) {
    activeRecord
        .maybeActive()
        .ifPresent(found -> mother.receivedOn(found, LocalDateTime.of(on, at)));
  }

  @When("the birth record was collected on {localDate}")
  public void collectedOn(final LocalDate on) {
    activeRecord.maybeActive().ifPresent(found -> mother.collectedOn(found, on));
  }

  @Given("the birth record has the patient born at {organization}")
  public void reportedAt(final OrganizationIdentifier organization) {
    activeRecord.maybeActive().ifPresent(found -> mother.bornAt(found, organization));
  }

  @Given("the birth record has the mother named {string} {string}")
  public void motherName(final String first, final String last) {
    motherFullName(first, null, last, null);
  }

  @Given("the birth record has the mother named {string} {string} {string} {nameSuffix}")
  public void motherFullName(
      final String first, final String middle, final String last, final String suffix) {
    activeRecord
        .maybeActive()
        .ifPresent(found -> mother.motherName(found, first, middle, last, suffix));
  }

  @Given(
      "the birth record has the mother living at {string} {string} {string} {state} {string} in {county}")
  public void motherFullAddress(
      final String address,
      final String address2,
      final String city,
      final String state,
      final String zip,
      final String county) {
    activeRecord
        .maybeActive()
        .ifPresent(
            found -> mother.motherAddress(found, address, address2, city, state, county, zip));
  }

  @Given("the birth record is associated with the investigation")
  public void associatedWith() {
    activeRecord
        .maybeActive()
        .ifPresent(found -> mother.associated(found, activeInvestigation.active()));
  }
}
