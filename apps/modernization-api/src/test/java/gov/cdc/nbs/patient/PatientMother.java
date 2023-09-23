package gov.cdc.nbs.patient;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.support.IdentificationMother;
import gov.cdc.nbs.support.RaceMother;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Locale;

@Component
@Transactional
public class PatientMother {

    private final Faker faker;
    private final MotherSettings settings;
    private final TestUniqueIdGenerator idGenerator;
    private final PatientLocalIdentifierGenerator localIdentifierGenerator;
    private final AddressIdentifierGenerator addressIdentifierGenerator;
    private final PatientShortIdentifierResolver resolver;
    private final EntityManager entityManager;
    private final TestAvailable<PatientIdentifier> available;
    private final TestActive<PatientIdentifier> active;
    private final TestPatientCleaner cleaner;

    PatientMother(
        final MotherSettings settings,
        final TestUniqueIdGenerator idGenerator,
        final PatientLocalIdentifierGenerator localIdentifierGenerator,
        final AddressIdentifierGenerator addressIdentifierGenerator,
        final PatientShortIdentifierResolver resolver,
        final EntityManager entityManager,
        final TestAvailable<PatientIdentifier> available,
        final TestActive<PatientIdentifier> active,
        final TestPatientCleaner cleaner
    ) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.localIdentifierGenerator = localIdentifierGenerator;
        this.addressIdentifierGenerator = addressIdentifierGenerator;
        this.resolver = resolver;
        this.entityManager = entityManager;
        this.available = available;
        this.active = active;
        this.cleaner = cleaner;
        this.faker = new Faker(new Locale("en-us"));
    }

    void reset() {
        this.cleaner.clean(settings.starting());
        this.available.reset();
    }

    public PatientIdentifier create() {

        long identifier = idGenerator.next();
        String local = localIdentifierGenerator.generate();

        Person patient = new Person(identifier, local);

        this.entityManager.persist(patient);

        long shortId = this.resolver.resolve(local).orElse(0L);

        PatientIdentifier patientIdentifier = new PatientIdentifier(patient.getId(), shortId, local);
        available.available(patientIdentifier);
        active.active(patientIdentifier);
        return patientIdentifier;
    }

    private Person managed(final PatientIdentifier identifier) {
        return this.entityManager.find(Person.class, identifier.id());
    }

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

    public void deleted(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.delete(
            new PatientCommand.Delete(
                identifier.id(),
                this.settings.createdBy(),
                this.settings.createdOn()
            ),
            id -> 0
        );
    }

    public void withAddress(
        final PatientIdentifier identifier,
        final String address,
        final String city,
        final String state,
        final String zip
    ) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddAddress(
                patient.getId(),
                idGenerator.next(),
                RandomUtil.getRandomDateInPast(),
                address,
                null,
                city,
                state,
                zip,
                null,
                "840",
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    public void withAddress(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddAddress(
                patient.getId(),
                idGenerator.next(),
                RandomUtil.getRandomDateInPast(),
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

    public void withIdentification(final PatientIdentifier identifier) {
        withIdentification(
                identifier,
                RandomUtil.getRandomFromArray(IdentificationMother.IDENTIFICATION_CODE_LIST),
                RandomUtil.getRandomNumericString(8)
        );
    }

    public void withIdentification(
            final PatientIdentifier identifier,
            final String type,
            final String value
    ) {
        Person patient = managed(identifier);

        patient.add(
                new PatientCommand.AddIdentification(
                        identifier.id(),
                        RandomUtil.getRandomDateInPast(),
                        value,
                        RandomUtil.maybeOneFrom("GA"),
                        type,
                        this.settings.createdBy(),
                        this.settings.createdOn()
                )
        );
    }

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

    public void withName(
        final PatientIdentifier identifier,
        final String type,
        final String first,
        final String last
    ) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddName(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                null,
                first,
                null,
                null,
                last,
                null,
                null,
                null,
                type,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    public void withPhone(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddPhone(
                identifier.id(),
                idGenerator.next(),
                RandomUtil.oneFrom("AN", "BP", "CP", "FAX", "PH"),
                RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.getRandomString(),
                faker.phoneNumber().cellPhone(),
                faker.phoneNumber().extension(),
                null,
                null,
                RandomUtil.getRandomString(),
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    public void withPhone(
        final PatientIdentifier identifier,
        final String number
    ) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddPhone(
                identifier.id(),
                idGenerator.next(),
                "PH",
                "H",
                RandomUtil.getRandomDateInPast(),
                null,
                number,
                null,
                null,
                null,
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    public void withEmail(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.add(
            new PatientCommand.AddPhone(
                identifier.id(),
                idGenerator.next(),
                "NET",
                RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
                RandomUtil.getRandomDateInPast(),
                null,
                null,
                null,
                faker.internet().emailAddress(),
                null,
                RandomUtil.getRandomString(),
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    public void withBirthday(
        final PatientIdentifier identifier,
        final LocalDate birthday
    ) {
        Person patient = managed(identifier);

        patient.update(
            new PatientCommand.UpdateBirth(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                birthday,
                null,
                RandomUtil.maybeIndicator(),
                null,
                null,
                null,
                null,
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            ),
            this.addressIdentifierGenerator
        );
    }

    public void withBirthInformation(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.update(
            new PatientCommand.UpdateBirth(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.dateInPast(),
                RandomUtil.maybeGender(),
                RandomUtil.maybeIndicator(),
                null,
                null,
                null,
                null,
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            ),
            this.addressIdentifierGenerator
        );
    }

    public void withGender(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.update(
            new PatientCommand.UpdateGender(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.gender().value(),
                null,
                null,
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }

    public void withMortality(final PatientIdentifier identifier) {

        Person patient = managed(identifier);

        Deceased indicator = RandomUtil.deceased();

        LocalDate deceasedOn = indicator == Deceased.Y ? RandomUtil.dateInPast() : null;

        patient.update(
            new PatientCommand.UpdateMortality(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                indicator.value(),
                deceasedOn,
                null,
                null,
                null,
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            ),
            this.addressIdentifierGenerator
        );

    }

    public void withEthnicity(final PatientIdentifier identifier) {
        Person patient = managed(identifier);

        patient.update(
            new PatientCommand.UpdateEthnicityInfo(
                identifier.id(),
                RandomUtil.getRandomDateInPast(),
                RandomUtil.ethnicity(),
                null,
                this.settings.createdBy(),
                this.settings.createdOn()
            )
        );
    }
}
