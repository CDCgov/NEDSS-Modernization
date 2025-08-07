package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.PatientService;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.springframework.stereotype.Component;

import static gov.cdc.nbs.patient.demographics.administrative.AdministrativePatientCommandMapper.asUpdateAdministrativeInfo;
import static gov.cdc.nbs.patient.demographics.birth.BirthDemographicPatientCommandMapper.asUpdateBirth;
import static gov.cdc.nbs.patient.demographics.gender.GenderDemographicPatientCommandMapper.asUpdateGender;
import static gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographicPatientCommandMapper.asUpdateGeneralInfo;
import static gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographicPatientCommandMapper.maybeAsAssociateStateHIVCase;
import static gov.cdc.nbs.patient.demographics.mortality.MortalityDemographicPatientCommandMapper.asUpdateMortality;

@Component
class PatientEditService {

  private final PatientService service;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final PermissionScopeResolver permissionScopeResolver;
  private final PatientEthnicityEditService ethnicityEditService;
  private final PatientAddressEditService addressEditService;
  private final PatientNameEditService nameEditService;

  PatientEditService(
      final PatientService service,
      final AddressIdentifierGenerator addressIdentifierGenerator,
      final PermissionScopeResolver permissionScopeResolver,
      final PatientEthnicityEditService ethnicityEditService,
      final PatientAddressEditService addressEditService,
      final PatientNameEditService nameEditService) {
    this.service = service;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.permissionScopeResolver = permissionScopeResolver;
    this.ethnicityEditService = ethnicityEditService;
    this.addressEditService = addressEditService;
    this.nameEditService = nameEditService;
  }

  void edit(
      final RequestContext context,
      final long patient,
      final EditedPatient changes) throws PatientException {

    this.service.using(
        patient,
        found -> edit(context, changes, found));

  }

  private void edit(
      final RequestContext context,
      final EditedPatient changes,
      final Person patient) {

    long identifier = patient.getId();

    changes.maybeAdministrative()
        .map(administrative -> asUpdateAdministrativeInfo(identifier, context, administrative))
        .ifPresent(patient::update);

    changes.maybeBirth()
        .map(demographic -> asUpdateBirth(identifier, context, demographic))
        .ifPresent(command -> patient.update(command, addressIdentifierGenerator));

    changes.maybeGender()
        .map(demographic -> asUpdateGender(identifier, context, demographic))
        .ifPresent(patient::update);

    changes.maybeMortality()
        .map(demographic -> asUpdateMortality(identifier, context, demographic))
        .ifPresent(command -> patient.update(command, addressIdentifierGenerator));

    changes.maybeGeneralInformation()
        .map(demographic -> asUpdateGeneralInfo(identifier, context, demographic))
        .ifPresent(patient::update);

    changes.maybeGeneralInformation()
        .flatMap(demographic -> maybeAsAssociateStateHIVCase(identifier, context, demographic))
        .ifPresent(association -> patient.associate(permissionScopeResolver, association));

    changes.maybeEthnicity().ifPresent(demographic -> ethnicityEditService.apply(context, patient, demographic));

    addressEditService.apply(context, patient, changes.addresses());
    nameEditService.apply(context, patient, changes.names());
  }

}
