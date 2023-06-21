package gov.cdc.nbs.patient;

import com.github.javafaker.Faker;
import gov.cdc.nbs.address.City;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.support.IdentificationMother;
import gov.cdc.nbs.support.RaceMother;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Locale;

@Component
public class PatientMother {

    private final Faker faker;
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
        this.faker = new Faker(new Locale("en-us"));
    }

    void reset() {
        this.cleaner.clean(settings.starting());
        this.identifiers.reset();
    }

    public Person create() {

        long identifier = idGenerator.next();
        String local = localIdentifierGenerator.generate();

        Person patient = new Person(identifier, local);

        this.entityManager.persist(patient);

        long shortId = this.resolver.resolve(local).orElse(0L);

        identifiers.available(new PatientIdentifier(patient.getId(), shortId, local));
        return patient;
    }

    private Person managed(final PatientIdentifier identifier) {
        return this.entityManager.find(Person.class, identifier.id());
    }

    @Transactional
    public PatientIdentifier revise(final PatientIdentifier identifier) {
        Person parent = managed(identifier);

        long id = idGenerator.next();

        Person revision = parent.revise(
            new PatientCommand.Revise(
                identifier.id(),
                id,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );

        this.entityManager.persist(revision);

        return new PatientIdentifier(id, identifier.shortId(), identifier.local());
    }

    @Transactional
    public void withAddress(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddAddress(
                patient.getId(),
                idGenerator.next(),
                faker.address().streetAddress(),
                null,
                faker.address().city(),
                RandomUtil.getRandomStateCode(),
                faker.address().zipCode(),
                null,
                RandomUtil.country(),
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    @Transactional
    public void withIdentification(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddIdentification(
                identifier.id(),
                RandomUtil.getRandomNumericString(8),
                "GA",
                RandomUtil.getRandomFromArray(IdentificationMother.IDENTIFICATION_CODE_LIST),
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    @Transactional
    public void withRace(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddRace(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.getRandomFromArray(RaceMother.RACE_LIST),
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    @Transactional
    public void withName(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddName(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                null,
                faker.name().firstName(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().lastName(),
                faker.name().lastName(),
                null,
                null,
                "L",
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    @Transactional
    public void withPhone(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddPhone(
                identifier.id(),
                idGenerator.next(),
                RandomUtil.oneFrom("AN", "BP", "CP", "NET", "FAX", "PH"),
                RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP","TMP"),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.getRandomString(),
                faker.phoneNumber().cellPhone(),
                faker.phoneNumber().extension(),
                faker.internet().emailAddress(),
                faker.internet().url(),
                RandomUtil.getRandomString(),
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }
}
