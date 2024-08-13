package gov.cdc.nbs.patient.profile;

import lombok.RequiredArgsConstructor;
import java.time.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.create.PatientCreator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.search.indexing.PatientIndexer;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import gov.cdc.nbs.patient.profile.address.change.NewPatientAddressInput;
import gov.cdc.nbs.patient.profile.address.change.PatientAddressChangeService;
import gov.cdc.nbs.patient.profile.names.change.NewPatientNameInput;
import gov.cdc.nbs.patient.profile.names.change.PatientNameChangeService;

@Slf4j
@RestController
@RequestMapping("/nbs/api/profile")
@PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('ADD-PATIENT')")
public class PatientCreateController {
  PatientCreateController(
      final Clock clock,
      final PatientCreator creator,
      final PatientNameChangeService nameService,
      final PatientAddressChangeService addressService,
      final PatientIndexer indexer) {
    this.clock = clock;
    this.creator = creator;
    this.nameService = nameService;
    this.addressService = addressService;
    this.indexer = indexer;
  }

  private final Clock clock;
  private final PatientCreator creator;
  private final PatientIndexer indexer;
  private final PatientNameChangeService nameService;
  private final PatientAddressChangeService addressService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public PatientIdentifier create(@RequestBody NewPatient newPatient) {
    var user = SecurityUtil.getUserDetails();
    RequestContext context = new RequestContext(user.getId(), Instant.now(this.clock));
    PatientInput input = new PatientInput();
    input.setComments(newPatient.comment());
    input.setAsOf(newPatient.asOf());
    PatientIdentifier created = creator.create(context, input);
    if (newPatient.names() != null) {
      newPatient.names().forEach(name -> {
        NewPatientNameInput newPatientNameInput = new NewPatientNameInput(
            created.id(),
            name.asOf(),
            name.type(),
            name.prefix(),
            name.first(),
            name.middle(),
            name.secondMiddle(),
            name.last(),
            name.secondLast(),
            name.suffix(),
            name.degree());
        nameService.add(context, newPatientNameInput);
      });
    }
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
