package gov.cdc.nbs.patient.profile.create;

import static gov.cdc.nbs.patient.demographics.address.AddressDemographicPatientCommandMapper.asAddAddress;
import static gov.cdc.nbs.patient.demographics.administrative.AdministrativePatientCommandMapper.asUpdateAdministrativeInfo;
import static gov.cdc.nbs.patient.demographics.birth.BirthDemographicPatientCommandMapper.asUpdateBirth;
import static gov.cdc.nbs.patient.demographics.ethnicity.EthnicityPatientCommandMapper.asAddDetailedEthnicity;
import static gov.cdc.nbs.patient.demographics.ethnicity.EthnicityPatientCommandMapper.asUpdateEthnicityInfo;
import static gov.cdc.nbs.patient.demographics.gender.GenderDemographicPatientCommandMapper.asUpdateGender;
import static gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographicPatientCommandMapper.asUpdateGeneralInfo;
import static gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographicPatientCommandMapper.maybeAsAssociateStateHIVCase;
import static gov.cdc.nbs.patient.demographics.identification.IdentificationDemographicPatientCommandMapper.asAddIdentification;
import static gov.cdc.nbs.patient.demographics.mortality.MortalityDemographicPatientCommandMapper.asUpdateMortality;
import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asAddName;
import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographicPatientCommandMapper.asAddPhone;
import static gov.cdc.nbs.patient.demographics.race.RaceDemographicPatientCommandMapper.asAddRace;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientIdentifierGenerator;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.demographics.ethnicity.EthnicityDemographic;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class PatientCreationService {

  private final SoundexResolver soudexResolver;
  private final PatientIdentifierGenerator patientIdentifierGenerator;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final PhoneIdentifierGenerator phoneIdentifierGenerator;
  private final PermissionScopeResolver permissionScopeResolver;
  private final EntityManager entityManager;

  PatientCreationService(
      final SoundexResolver soudexResolver,
      final PatientIdentifierGenerator patientIdentifierGenerator,
      final AddressIdentifierGenerator addressIdentifierGenerator,
      final PhoneIdentifierGenerator phoneIdentifierGenerator,
      final PermissionScopeResolver permissionScopeResolver,
      final EntityManager entityManager) {
    this.soudexResolver = soudexResolver;
    this.patientIdentifierGenerator = patientIdentifierGenerator;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.phoneIdentifierGenerator = phoneIdentifierGenerator;
    this.permissionScopeResolver = permissionScopeResolver;
    this.entityManager = entityManager;
  }

  @Transactional
  public CreatedPatient create(final RequestContext context, final NewPatient newPatient) {
    PatientIdentifier identifier = patientIdentifierGenerator.generate();

    Person patient =
        new Person(
            new PatientCommand.CreatePatient(
                identifier.id(), identifier.local(), context.requestedBy(), context.requestedAt()));

    newPatient
        .maybeAdministrative()
        .map(administrative -> asUpdateAdministrativeInfo(identifier.id(), context, administrative))
        .ifPresent(patient::update);

    newPatient
        .maybeBirth()
        .map(demographic -> asUpdateBirth(identifier.id(), context, demographic))
        .ifPresent(command -> patient.update(command, addressIdentifierGenerator));

    newPatient
        .maybeGender()
        .map(demographic -> asUpdateGender(identifier.id(), context, demographic))
        .ifPresent(patient::update);

    newPatient
        .maybeEthnicity()
        .filter(demographic -> demographic.ethnicGroup() != null)
        .map(demographic -> asUpdateEthnicityInfo(identifier.id(), context, demographic))
        .ifPresent(patient::update);

    newPatient
        .maybeEthnicity()
        .filter(demographic -> demographic.ethnicGroup() != null)
        .map(EthnicityDemographic::detailed)
        .stream()
        .flatMap(Collection::stream)
        .map(demographic -> asAddDetailedEthnicity(identifier.id(), context, demographic))
        .forEach(patient::add);

    newPatient
        .maybeGeneralInformation()
        .map(demographic -> asUpdateGeneralInfo(identifier.id(), context, demographic))
        .ifPresent(patient::update);

    newPatient
        .maybeGeneralInformation()
        .flatMap(demographic -> maybeAsAssociateStateHIVCase(identifier.id(), context, demographic))
        .ifPresent(association -> patient.associate(permissionScopeResolver, association));

    newPatient.names().stream()
        .map(demographic -> asAddName(identifier.id(), context, demographic))
        .forEach(name -> patient.add(this.soudexResolver, name));

    newPatient
        .maybeMortality()
        .map(demographic -> asUpdateMortality(identifier.id(), context, demographic))
        .ifPresent(command -> patient.update(command, addressIdentifierGenerator));

    newPatient.identifications().stream()
        .map(demographic -> asAddIdentification(identifier.id(), context, demographic))
        .forEach(patient::add);

    newPatient.races().stream()
        .map(demographic -> asAddRace(identifier.id(), context, demographic))
        .forEach(patient::add);

    newPatient.phoneEmails().stream()
        .map(demographic -> asAddPhone(identifier.id(), context, demographic))
        .forEach(command -> patient.add(command, phoneIdentifierGenerator));

    newPatient.addresses().stream()
        .map(demographic -> asAddAddress(identifier.id(), context, demographic))
        .forEach(command -> patient.add(command, addressIdentifierGenerator));

    this.entityManager.persist(patient);

    CreatedPatient.Name legalName =
        patient.legalName(LocalDate.now()).map(this::asName).orElse(null);

    return new CreatedPatient(identifier.id(), identifier.shortId(), identifier.local(), legalName);
  }

  private CreatedPatient.Name asName(final PersonName name) {
    return new CreatedPatient.Name(name.first(), name.last());
  }
}
