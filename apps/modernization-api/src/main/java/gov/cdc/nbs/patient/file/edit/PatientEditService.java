package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.PatientService;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.springframework.stereotype.Component;

import static gov.cdc.nbs.patient.demographics.administrative.AdministrativePatientCommandMapper.asClearAdministrativeInformation;
import static gov.cdc.nbs.patient.demographics.administrative.AdministrativePatientCommandMapper.asUpdateAdministrativeInfo;
import static gov.cdc.nbs.patient.demographics.birth.BirthDemographicPatientCommandMapper.asClearBirthDemographics;
import static gov.cdc.nbs.patient.demographics.birth.BirthDemographicPatientCommandMapper.asUpdateBirth;
import static gov.cdc.nbs.patient.demographics.ethnicity.EthnicityPatientCommandMapper.asClearEthnicityDemographics;
import static gov.cdc.nbs.patient.demographics.gender.GenderDemographicPatientCommandMapper.asClearGenderDemographics;
import static gov.cdc.nbs.patient.demographics.gender.GenderDemographicPatientCommandMapper.asUpdateGender;
import static gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographicPatientCommandMapper.*;
import static gov.cdc.nbs.patient.demographics.mortality.MortalityDemographicPatientCommandMapper.asClearMoralityDemographics;
import static gov.cdc.nbs.patient.demographics.mortality.MortalityDemographicPatientCommandMapper.asUpdateMortality;

@Component
class PatientEditService {

  private final PatientService service;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final PermissionScopeResolver permissionScopeResolver;
  private final PatientEthnicityEditService ethnicityEditService;
  private final PatientAddressEditService addressEditService;
  private final PatientNameEditService nameEditService;
  private final PatientRaceEditService raceEditService;
  private final PatientPhoneEditService phoneEditService;
  private final PatientIdentificationEditService identificationEditService;

  PatientEditService(
      final PatientService service,
      final AddressIdentifierGenerator addressIdentifierGenerator,
      final PermissionScopeResolver permissionScopeResolver,
      final PatientEthnicityEditService ethnicityEditService,
      final PatientAddressEditService addressEditService,
      final PatientNameEditService nameEditService,
      final PatientPhoneEditService phoneEditService,
      final PatientIdentificationEditService identificationEditService,
      final PatientRaceEditService raceEditService
  ) {
    this.service = service;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.permissionScopeResolver = permissionScopeResolver;
    this.ethnicityEditService = ethnicityEditService;
    this.addressEditService = addressEditService;
    this.nameEditService = nameEditService;
    this.phoneEditService = phoneEditService;
    this.identificationEditService = identificationEditService;
    this.raceEditService = raceEditService;
  }

  void edit(
      final RequestContext context,
      final long patient,
      final EditedPatient changes
  ) throws PatientException {

    this.service.using(
        patient,
        found -> edit(context, changes, found)
    );

  }

  private void edit(
      final RequestContext context,
      final EditedPatient changes,
      final Person patient
  ) {

    long identifier = patient.id();

    changes.maybeAdministrative()
        .map(administrative -> asUpdateAdministrativeInfo(identifier, context, administrative))
        .ifPresentOrElse(patient::update, () -> patient.clear(asClearAdministrativeInformation(identifier, context)));

    changes.maybeGender()
        .map(demographic -> asUpdateGender(identifier, context, demographic))
        .ifPresentOrElse(
            patient::update,
            () -> patient.clear(asClearGenderDemographics(identifier, context))
        );

    changes.maybeBirth()
        .map(demographic -> asUpdateBirth(identifier, context, demographic))
        .ifPresentOrElse(
            command -> patient.update(command, addressIdentifierGenerator),
            () -> patient.clear(asClearBirthDemographics(identifier, context))
        );

    changes.maybeMortality()
        .map(demographic -> asUpdateMortality(identifier, context, demographic))
        .ifPresentOrElse(
            command -> patient.update(command, addressIdentifierGenerator),
            () -> patient.clear(asClearMoralityDemographics(identifier, context))
        );

    changes.maybeGeneralInformation()
        .map(demographic -> asUpdateGeneralInfo(identifier, context, demographic))
        .ifPresentOrElse(
            patient::update,
            () -> patient.clear(asClearGeneralInformationDemographics(identifier, context))
        );

    changes.maybeGeneralInformation()
        .flatMap(demographic -> maybeAsAssociateStateHIVCase(identifier, context, demographic))
        .ifPresentOrElse(
            association -> patient.associate(permissionScopeResolver, association),
            () -> patient.disassociate(permissionScopeResolver, asDisassociateStateHIVCase(identifier, context))
        );

    changes.maybeEthnicity()
        .ifPresentOrElse(
            demographic -> ethnicityEditService.apply(context, patient, demographic),
            () -> patient.clear(asClearEthnicityDemographics(patient.id(), context))
        );

    addressEditService.apply(context, patient, changes.addresses());
    nameEditService.apply(context, patient, changes.names());
    phoneEditService.apply(context, patient, changes.phoneEmails());
    identificationEditService.apply(context, patient, changes.identifications());
    raceEditService.apply(context, patient, changes.races());
  }

}
