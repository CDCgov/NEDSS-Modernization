package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.PatientIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static gov.cdc.nbs.patient.profile.administrative.AdministrativePatientCommandMapper.asUpdateAdministrativeInfo;
import static gov.cdc.nbs.patient.profile.ethnicity.EthnicityPatientCommandMapper.asUpdateEthnicityInfo;
import static gov.cdc.nbs.patient.profile.birth.BirthDemographicPatientCommandMapper.asUpdateBirth;
import static gov.cdc.nbs.patient.profile.gender.GenderDemographicPatientCommandMapper.asUpdateGender;
import static gov.cdc.nbs.patient.profile.names.NameDemographicPatientCommandMapper.asAddName;

@Component
class PatientCreationService {

  private final PatientIdentifierGenerator patientIdentifierGenerator;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final EntityManager entityManager;

  PatientCreationService(
      final PatientIdentifierGenerator patientIdentifierGenerator,
      AddressIdentifierGenerator addressIdentifierGenerator,
      final EntityManager entityManager) {
    this.patientIdentifierGenerator = patientIdentifierGenerator;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.entityManager = entityManager;
  }

  @Transactional
  public PatientIdentifier create(
      final RequestContext context,
      final NewPatient newPatient) {
    PatientIdentifier identifier = patientIdentifierGenerator.generate();

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            identifier.id(),
            identifier.local(),
            context.requestedBy(),
            context.requestedAt()))
                .update(
                    asUpdateAdministrativeInfo(
                        identifier.id(),
                        context,
                        newPatient.administrative()));

    if (newPatient.ethnicity() != null)
      patient.update(
          asUpdateEthnicityInfo(
              identifier.id(),
              context,
              newPatient.ethnicity()));

    newPatient.maybeBirth()
        .map(demographic -> asUpdateBirth(identifier.id(), context, demographic))
        .ifPresent(command -> patient.update(command, addressIdentifierGenerator));

    newPatient.maybeGender()
        .map(demographic -> asUpdateGender(identifier.id(), context, demographic))
        .ifPresent(patient::update);

    newPatient.names()
        .stream()
        .map(demographic -> asAddName(identifier.id(), context, demographic))
        .forEach(patient::add);

    this.entityManager.persist(patient);

    return identifier;
  }

}
