package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientIdentifierGenerator;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.ethnicity.EthnicityDemographic;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

import static gov.cdc.nbs.patient.profile.administrative.AdministrativePatientCommandMapper.asUpdateAdministrativeInfo;
import static gov.cdc.nbs.patient.profile.birth.BirthDemographicPatientCommandMapper.asUpdateBirth;
import static gov.cdc.nbs.patient.profile.ethnicity.EthnicityPatientCommandMapper.asAddDetailedEthnicity;
import static gov.cdc.nbs.patient.profile.ethnicity.EthnicityPatientCommandMapper.asUpdateEthnicityInfo;
import static gov.cdc.nbs.patient.profile.gender.GenderDemographicPatientCommandMapper.asUpdateGender;
import static gov.cdc.nbs.patient.profile.names.NameDemographicPatientCommandMapper.asAddName;

@Component
class PatientCreationService {

  private final PatientIdentifierGenerator patientIdentifierGenerator;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final EntityManager entityManager;

  PatientCreationService(
      final PatientIdentifierGenerator patientIdentifierGenerator,
      final AddressIdentifierGenerator addressIdentifierGenerator,
      final EntityManager entityManager
  ) {
    this.patientIdentifierGenerator = patientIdentifierGenerator;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.entityManager = entityManager;
  }

  @Transactional
  public CreatedPatient create(
      final RequestContext context,
      final NewPatient newPatient
  ) {
    PatientIdentifier identifier = patientIdentifierGenerator.generate();

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            identifier.id(),
            identifier.local(),
            context.requestedBy(),
            context.requestedAt()
        )
    );

    newPatient.maybeAdministrative()
        .map(administrative -> asUpdateAdministrativeInfo(identifier.id(), context, administrative))
        .ifPresent(patient::update);

    newPatient.maybeBirth()
        .map(demographic -> asUpdateBirth(identifier.id(), context, demographic))
        .ifPresent(command -> patient.update(command, addressIdentifierGenerator));

    newPatient.maybeGender()
        .map(demographic -> asUpdateGender(identifier.id(), context, demographic))
        .ifPresent(patient::update);

    newPatient.maybeEthnicity()
        .map(demographic -> asUpdateEthnicityInfo(identifier.id(), context, demographic))
        .ifPresent(patient::update);

    newPatient.maybeEthnicity()
        .map(EthnicityDemographic::detailed)
        .stream()
        .flatMap(Collection::stream)
        .map(demographic -> asAddDetailedEthnicity(identifier.id(), context, demographic))
        .forEach(patient::add);

    newPatient.names()
        .stream()
        .map(demographic -> asAddName(identifier.id(), context, demographic))
        .forEach(patient::add);

    this.entityManager.persist(patient);

    CreatedPatient.Name legalName = patient.legalName(LocalDate.now())
        .map(this::asName)
        .orElse(null);

    return new CreatedPatient(
        identifier.id(),
        identifier.shortId(),
        identifier.local(),
        legalName
    );
  }

  private CreatedPatient.Name asName(final PersonName name) {
    return new CreatedPatient.Name(name.getFirstNm(), name.getLastNm());
  }
}
