package gov.cdc.nbs.testing.event.contact;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.patient.RevisionMother;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ContactRecordSteps {

  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<PatientIdentifier> activePatient;
  private final Available<PatientIdentifier> availablePatients;
  private final Active<ContactRecordIdentifier> activeContactRecord;
  private final RevisionMother revisionMother;
  private final ContactRecordMother mother;

  ContactRecordSteps(
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<PatientIdentifier> activePatient,
      final Available<PatientIdentifier> availablePatients,
      final Active<ContactRecordIdentifier> activeContactRecord,
      final RevisionMother revisionMother,
      final ContactRecordMother mother
  ) {
    this.activeInvestigation = activeInvestigation;
    this.activePatient = activePatient;
    this.availablePatients = availablePatients;
    this.activeContactRecord = activeContactRecord;
    this.revisionMother = revisionMother;
    this.mother = mother;
  }


  @Given("the patient names the previous patient as a contact on the investigation")
  public void namesPrevious() {
    this.activeInvestigation.maybeActive()
        .ifPresent(investigation -> mother.create(
                revisionMother.revise(availablePatients.previous()),
                investigation
            )
        );
  }

  @Given("the patient was named as a contact in the investigation")
  public void named() {
    this.activeInvestigation.maybeActive()
        .ifPresent(investigation -> mother.create(
                revisionMother.revise(activePatient.active()),
                investigation
            )
        );
  }

  @Given("the contact record was created on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.createdOn(contact, LocalDateTime.of(on, at)));
  }

  @Given("the contact record was named on {localDate}")
  public void namedOn(final LocalDate value) {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.namedOn(contact, value));
  }

  @Given("the contact record has a {priority} priority")
  public void priority(final String value) {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.priority(contact, value));
  }

  @Given("the contact record has a {disposition} disposition")
  public void disposition(final String value) {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.disposition(contact, value));
  }

  @Given("the contact record has a {referralBasis} referral basis")
  public void referralBasis(final String value) {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.referralBasis(contact, value));
  }

  @Given("the contact record has a {stdProcessingDecision} processing decision")
  public void processingDecision(final String value) {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.processingDecision(contact, value));
  }


  @Given("the contact record is associated with the investigation")
  public void associated() {
    this.activeContactRecord.maybeActive()
        .ifPresent(contact -> mother.associated(contact, activeInvestigation.active()));
  }
}
