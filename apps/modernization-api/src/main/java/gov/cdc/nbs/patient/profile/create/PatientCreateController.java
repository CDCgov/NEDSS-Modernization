package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.address.change.NewPatientAddressInput;
import gov.cdc.nbs.patient.profile.address.change.PatientAddressChangeService;
import gov.cdc.nbs.patient.profile.identification.change.NewPatientIdentificationInput;
import gov.cdc.nbs.patient.profile.identification.change.PatientIdentificationChangeService;
import gov.cdc.nbs.patient.profile.phone.change.NewPatientPhoneInput;
import gov.cdc.nbs.patient.profile.phone.change.PatientPhoneChangeService;
import gov.cdc.nbs.patient.profile.race.change.PatientRaceChangeService;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@RestController
@RequestMapping("/nbs/api/profile")
@PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('ADD-PATIENT')")
@SuppressWarnings("squid:S107")
public class PatientCreateController {

  private final Clock clock;
  private final PatientCreationService service;
  private final PatientIndexer indexer;
  private final PatientAddressChangeService addressService;
  private final PatientPhoneChangeService phoneService;
  private final PatientRaceChangeService raceService;
  private final PatientIdentificationChangeService identificationService;

  PatientCreateController(
      final Clock clock,
      final PatientCreationService service,
      final PatientAddressChangeService addressService,
      final PatientPhoneChangeService phoneService,
      final PatientRaceChangeService raceService,
      final PatientIdentificationChangeService identificationService,
      final PatientIndexer indexer) {
    this.clock = clock;
    this.service = service;
    this.addressService = addressService;
    this.phoneService = phoneService;
    this.raceService = raceService;
    this.identificationService = identificationService;
    this.indexer = indexer;
  }

  @Operation(
      summary = "PatientProfile",
      description = "Allows creation of a patient",
      tags = "PatientProfile")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreatedPatient create(@RequestBody final NewPatient newPatient) {
    var user = SecurityUtil.getUserDetails();
    RequestContext context = new RequestContext(user.getId(), Instant.now(this.clock));

    CreatedPatient created = service.create(context, newPatient);

    if (newPatient.addresses() != null) {
      newPatient.addresses().forEach(address -> {
        NewPatientAddressInput newPatientAddressInput = new NewPatientAddressInput(
            created.id(),
            address.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant(),
            address.type(),
            address.use(),
            address.address1(),
            address.address2(),
            address.city(),
            address.state(),
            address.zipcode(),
            address.county(),
            address.censusTract(),
            address.country(),
            address.comment());
        addressService.add(context, newPatientAddressInput);
      });
    }
    if (newPatient.phoneEmails() != null) {
      newPatient.phoneEmails().forEach(phone -> {
        NewPatientPhoneInput newPatientPhoneInput = new NewPatientPhoneInput(
            created.id(),
            phone.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant(),
            phone.type(),
            phone.use(),
            phone.countryCode(),
            phone.phoneNumber(),
            phone.extension(),
            phone.email(),
            phone.url(),
            phone.comment());
        phoneService.add(context, newPatientPhoneInput);
      });
    }
    if (newPatient.races() != null) {
      newPatient.races().forEach(race -> {
        RaceInput newRaceInput = new RaceInput();
        newRaceInput.setPatient(created.id());
        newRaceInput.setAsOf(race.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant());
        newRaceInput.setCategory(race.race());
        newRaceInput.setDetailed(race.detailed());
        raceService.add(context, newRaceInput);
      });
    }
    if (newPatient.identifications() != null) {
      newPatient.identifications().forEach(identification -> {
        NewPatientIdentificationInput newPatientIdentificationInput = new NewPatientIdentificationInput(
            created.id(),
            identification.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant(),
            identification.type(),
            identification.issuer(),
            identification.id());
        identificationService.add(context, newPatientIdentificationInput);
      });
    }
    this.indexer.index(created.id());
    return created;
  }
}

