package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.address.change.NewPatientAddressInput;
import gov.cdc.nbs.patient.profile.address.change.PatientAddressChangeService;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/nbs/api/profile")
@PreAuthorize("hasAuthority('ADD-PATIENT')")
@SuppressWarnings("squid:S107")
public class PatientCreateController {

  private final Clock clock;
  private final PatientCreationService service;
  private final PatientIndexer indexer;
  private final PatientAddressChangeService addressService;

  PatientCreateController(
      final Clock clock,
      final PatientCreationService service,
      final PatientAddressChangeService addressService,
      final PatientIndexer indexer
  ) {
    this.clock = clock;
    this.service = service;
    this.addressService = addressService;
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
    RequestContext context = new RequestContext(user.getId(), LocalDateTime.now(this.clock));

    CreatedPatient created = service.create(context, newPatient);

    if (newPatient.addresses() != null) {
      newPatient.addresses().forEach(address -> {
        NewPatientAddressInput newPatientAddressInput = new NewPatientAddressInput(
            created.id(),
            address.asOf(),
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

    this.indexer.index(created.id());
    return created;
  }
}

