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
      final BirthRecordMother mother
  ) {
    this.activePatient = activePatient;
    this.activeRecord = activeRecord;
    this.activeInvestigation = activeInvestigation;
    this.mother = mother;
  }

  @Given("the patient has a birth record")
  public void has() {
    has(RandomUtil.getRandomString(50));
  }

  @Given("the patient has the {string} birth record")
  public void has(final String certificate) {
    activePatient.maybeActive()
        .ifPresent(patient -> mother.create(patient, certificate));
  }

  @When("the birth record was received on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) {
    activeRecord.maybeActive()
        .ifPresent(
            record -> mother.receivedOn(
                record,
                LocalDateTime.of(on, at)
            )
        );
  }

  @When("the birth record was collected on {localDate}")
  public void collectedOn(final LocalDate on) {
    activeRecord.maybeActive()
        .ifPresent(record -> mother.collectedOn(record, on));
  }

  @Given("the birth record has the patient born at {organization}")
  public void reportedAt(final OrganizationIdentifier organization) {
    activeRecord.maybeActive()
        .ifPresent(record -> mother.bornAt(record, organization));
  }

  @Given("the birth record is associated with the investigation")
  public void associatedWith() {
    activeRecord.maybeActive()
        .ifPresent(record -> mother.associated(record, activeInvestigation.active()));
  }

}
