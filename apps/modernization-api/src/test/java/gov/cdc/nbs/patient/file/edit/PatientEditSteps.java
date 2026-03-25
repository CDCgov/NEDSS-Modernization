package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.patient.demographics.address.AddressDemographic;
import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import gov.cdc.nbs.patient.demographics.birth.BirthDemographic;
import gov.cdc.nbs.patient.demographics.ethnicity.EthnicityDemographic;
import gov.cdc.nbs.patient.demographics.gender.GenderDemographic;
import gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographic;
import gov.cdc.nbs.patient.demographics.identification.IdentificationDemographic;
import gov.cdc.nbs.patient.demographics.mortality.MortalityDemographic;
import gov.cdc.nbs.patient.demographics.name.NameDemographic;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import gov.cdc.nbs.patient.demographics.race.RaceDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientEditSteps {

  private final Active<Administrative> activeAdministrative;
  private final Available<AddressDemographic> availableAddresses;
  private final Available<PhoneDemographic> availablePhones;
  private final Available<NameDemographic> availableNames;
  private final Available<IdentificationDemographic> availableIdentifications;
  private final Available<RaceDemographic> availableRaces;
  //
  private final Active<BirthDemographic> activeBirthDemographic;
  private final Active<GenderDemographic> activeGenderDemographic;
  private final Active<EthnicityDemographic> activeEthnicity;
  private final Active<MortalityDemographic> activeMortalityDemographic;
  private final Active<GeneralInformationDemographic> activeGeneralInformation;
  //
  private final Active<EditedPatient> activeEntry;
  private final PatientEditRequester requester;
  private final Active<ResultActions> response;
  private final Active<PatientIdentifier> activePatient;

  PatientEditSteps(
      final Active<Administrative> activeAdministrative,
      final Available<AddressDemographic> availableAddresses,
      final Active<BirthDemographic> activeBirthDemographic,
      final Active<GenderDemographic> activeGenderDemographic,
      final Available<NameDemographic> availableNames,
      final Active<EthnicityDemographic> activeEthnicity,
      final Active<MortalityDemographic> activeMortalityDemographic,
      final Active<GeneralInformationDemographic> activeGeneralInformation,
      final Available<PhoneDemographic> availablePhones,
      final Available<IdentificationDemographic> availableIdentifications,
      final Available<RaceDemographic> availableRaces,
      final Active<EditedPatient> activeEntry,
      final PatientEditRequester requester,
      final Active<ResultActions> response,
      final Active<PatientIdentifier> activePatient) {
    this.activeAdministrative = activeAdministrative;
    this.availableAddresses = availableAddresses;
    this.activeBirthDemographic = activeBirthDemographic;
    this.activeGenderDemographic = activeGenderDemographic;
    this.availableNames = availableNames;
    this.activeEthnicity = activeEthnicity;
    this.activeMortalityDemographic = activeMortalityDemographic;
    this.activeGeneralInformation = activeGeneralInformation;
    this.availablePhones = availablePhones;
    this.availableIdentifications = availableIdentifications;
    this.availableRaces = availableRaces;
    this.activeEntry = activeEntry;
    this.requester = requester;
    this.response = response;
    this.activePatient = activePatient;
  }

  @When("I edit the patient with entered demographics")
  public void edit() {
    this.activeEntry.reset();
    this.activePatient.maybeActive().ifPresent(patient -> edit(patient.id()));
  }

  @When("I edit an unknown patient with entered demographics")
  public void editUnknown() {
    edit(Long.MAX_VALUE);
  }

  private void edit(final long patient) {
    this.activeAdministrative
        .maybeActive()
        .ifPresent(
            administrative ->
                this.activeEntry.active(current -> current.withAdministrative(administrative)));

    this.availableNames
        .all()
        .forEach(name -> this.activeEntry.active(current -> current.withName(name)));

    this.availableAddresses
        .all()
        .forEach(address -> this.activeEntry.active(current -> current.withAddress(address)));

    this.availablePhones
        .all()
        .forEach(phone -> this.activeEntry.active(current -> current.withPhoneEmail(phone)));

    this.availableIdentifications
        .all()
        .forEach(
            identification ->
                this.activeEntry.active(current -> current.withIdentification(identification)));

    this.availableRaces
        .all()
        .forEach(race -> this.activeEntry.active(current -> current.withRace(race)));

    this.activeEthnicity
        .maybeActive()
        .ifPresent(
            ethnicity -> this.activeEntry.active(current -> current.withEthnicity(ethnicity)));

    this.activeBirthDemographic
        .maybeActive()
        .ifPresent(
            demographic -> this.activeEntry.active(current -> current.withBirth(demographic)));

    this.activeGenderDemographic
        .maybeActive()
        .ifPresent(
            demographic -> this.activeEntry.active(current -> current.withGender(demographic)));

    this.activeMortalityDemographic
        .maybeActive()
        .ifPresent(
            demographic -> this.activeEntry.active(current -> current.withMortality(demographic)));

    this.activeGeneralInformation
        .maybeActive()
        .ifPresent(
            demographic ->
                this.activeEntry.active(current -> current.withGeneralInformation(demographic)));

    this.activeEntry
        .maybeActive()
        .map(changes -> this.requester.edit(patient, changes))
        .ifPresent(this.response::active);
  }
}
