package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.TestAvailable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class PatientMother {

    private final MotherSettings settings;
    private final TestUniqueIdGenerator idGenerator;
    private final PatientLocalIdentifierGenerator localIdentifierGenerator;

    private final PatientShortIdentifierResolver resolver;
    private final EntityManager entityManager;
    private final TestAvailable<PatientIdentifier> identifiers;
    private final TestPatientCleaner cleaner;

    public PatientMother(
        final MotherSettings settings,
        final TestUniqueIdGenerator idGenerator,
        final PatientLocalIdentifierGenerator localIdentifierGenerator,
        final PatientShortIdentifierResolver resolver,
        final EntityManager entityManager,
        final TestAvailable<PatientIdentifier> patients,
        final TestPatientCleaner cleaner
    ) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.localIdentifierGenerator = localIdentifierGenerator;
        this.resolver = resolver;
        this.entityManager = entityManager;
        this.identifiers = patients;
        this.cleaner = cleaner;
    }

    void reset() {
        this.cleaner.clean(settings.starting());
        this.identifiers.reset();
    }

    public Person create() {

        String local = localIdentifierGenerator.generate();

        Person patient = PersonMother.generateRandomPerson(idGenerator.next(), local);

        this.entityManager.persist(patient);

        long shortId = this.resolver.resolve(local).orElse(0L);

        identifiers.available(new PatientIdentifier(patient.getId(),  shortId, local));
        return patient;
    }
}
