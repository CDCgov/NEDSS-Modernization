package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.support.IdentificationMother;
import gov.cdc.nbs.support.RaceMother;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Component
@Transactional
public class PatientMother {

  private final Faker faker;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final PatientLocalIdentifierGenerator localIdentifierGenerator;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final PatientShortIdentifierResolver shortIdentifierResolver;
  private final EntityManager entityManager;
  private final Available<PatientIdentifier> available;
  private final Active<PatientIdentifier> active;
  private final PatientCleaner cleaner;
  private final RevisionPatientCreator revisionCreator;
  private final JdbcClient jdbcClient;
  private final SoundexResolver soundexResolver;

  PatientMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final PatientLocalIdentifierGenerator localIdentifierGenerator,
      final AddressIdentifierGenerator addressIdentifierGenerator,
      final PatientShortIdentifierResolver shortIdentifierResolver,
      final EntityManager entityManager,
      final Available<PatientIdentifier> available,
      final Active<PatientIdentifier> active,
      final PatientCleaner cleaner,
      final RevisionPatientCreator revisionCreator,
      final JdbcClient jdbcClient,
      final SoundexResolver soundexResolver
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.localIdentifierGenerator = localIdentifierGenerator;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.shortIdentifierResolver = shortIdentifierResolver;
    this.entityManager = entityManager;
    this.available = available;
    this.active = active;
    this.cleaner = cleaner;
    this.revisionCreator = revisionCreator;
    this.jdbcClient = jdbcClient;
    this.soundexResolver = soundexResolver;
    this.faker = new Faker(Locale.of("en-us"));
  }

  void reset() {

    this.cleaner.clean();
    this.available.reset();
  }

  public PatientIdentifier create() {
    PatientIdentifier created = patient();
    available.available(created);
    active.active(created);
    return created;
  }

  public PatientIdentifier available() {
    PatientIdentifier created = patient();
    available.available(created);
    return created;
  }

  private PatientIdentifier patient() {

    long identifier = idGenerator.next();
    String local = localIdentifierGenerator.generate();

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            identifier,
            local,
            settings.createdBy(),
            settings.createdOn()
        )
    );

    this.entityManager.persist(patient);

    long shortId = this.shortIdentifierResolver.resolve(local).orElse(0L);

    return new PatientIdentifier(patient.getId(), shortId, local);
  }

  private Person managed(final PatientIdentifier identifier) {
    return this.entityManager.find(Person.class, identifier.id());
  }

  public PatientIdentifier revise(final PatientIdentifier identifier) {
    return this.revisionCreator.revise(identifier);
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

  public void superseded(final PatientIdentifier identifier) {
    managed(identifier)
        .recordStatus()
        .change("SUPERCEDED", LocalDateTime.now());
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip
  ) {
    withAddress(identifier, "H", address, city, county, state, zip);
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip
  ) {
    withAddress(identifier, "H", use, address, city, county, state, zip);
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip
  ) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddAddress(
            patient.getId(),
            idGenerator.next(),
            RandomUtil.dateInPast(),
            type,
            use,
            address,
            null,
            city,
            state,
            zip,
            county,
            "840",
            null,
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
            RandomUtil.dateInPast(),
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
      final String value) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddIdentification(
            identifier.id(),
            RandomUtil.dateInPast(),
            value,
            RandomUtil.maybeOneFrom("GA"),
            type,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withRace(final PatientIdentifier identifier) {
    withRace(identifier, RandomUtil.getRandomFromArray(RaceMother.RACE_LIST));
  }

  public void withRace(
      final PatientIdentifier identifier,
      final String race
  ) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddRace(
            identifier.id(),
            RandomUtil.dateInPast(),
            race,
            this.settings.createdBy(),
            this.settings.createdOn()));
  }

  public void withRaceIncluding(
      final PatientIdentifier identifier,
      final String race,
      final String detail
  ) {
    Person patient = managed(identifier);

    patient.update(
        new PatientCommand.UpdateRaceInfo(
            identifier.id(),
            RandomUtil.dateInPast(),
            race,
            List.of(detail),
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withName(final PatientIdentifier identifier) {
    withName(
        identifier,
        RandomUtil.dateInPast(),
        "L",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        null
    );
  }

  public void withName(
      final PatientIdentifier identifier,
      final String type,
      final String first,
      final String last
  ) {
    withName(
        identifier,
        RandomUtil.dateInPast(),
        type,
        first,
        last
    );
  }

  public void withName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String type,
      final String first,
      final String last
  ) {
    withName(
        identifier,
        asOf,
        type,
        first,
        null,
        last,
        null
    );
  }

  public void withName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix
  ) {
    Person patient = managed(identifier);

    patient.add(
        this.soundexResolver,
        new PatientCommand.AddName(
            identifier.id(),
            asOf,
            null,
            first,
            middle,
            null,
            last,
            null,
            suffix,
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
            RandomUtil.dateInPast(),
            RandomUtil.getRandomString(15),
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
    withPhone(identifier, null, number, null);
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String countryCode,
      final String number,
      final String extension
  ) {
    withPhone(identifier, "PH", "H", countryCode, number, extension);
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String countryCode,
      final String number,
      final String extension
  ) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddPhone(
            identifier.id(),
            idGenerator.next(),
            type,
            use,
            RandomUtil.dateInPast(),
            countryCode,
            number,
            extension,
            null,
            null,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withEmail(final PatientIdentifier identifier) {
    withEmail(identifier, faker.internet().emailAddress());
  }

  public void withEmail(final PatientIdentifier identifier, final String email) {
    withEmail(
        identifier,
        "NET",
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        email,
        RandomUtil.dateInPast()
    );
  }

  public void withEmail(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String email,
      final LocalDate asOf
  ) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddPhone(
            identifier.id(),
            idGenerator.next(),
            type,
            use,
            asOf,
            null,
            null,
            null,
            email,
            null,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withEmail(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String email
  ) {
    withEmail(
        identifier,
        type,
        use,
        email,
        RandomUtil.dateInPast()
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
            RandomUtil.dateInPast(),
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
            RandomUtil.dateInPast(),
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
    withGender(identifier, RandomUtil.gender().value());
  }


  public void withGender(final PatientIdentifier identifier, final String gender) {
    Person patient = managed(identifier);

    patient.update(
        new PatientCommand.UpdateGender(
            identifier.id(),
            RandomUtil.dateInPast(),
            gender,
            null,
            null,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withLocalId(final PatientIdentifier identifier, final String localId) {
    Person patient = managed(identifier);
    patient.setLocalId(localId);
  }

  public void withId(final PatientIdentifier identifier, final long id) {
    Person patient = managed(identifier);

    patient.setId(id);
  }

  public void withMortality(final PatientIdentifier identifier) {

    Person patient = managed(identifier);

    Deceased indicator = RandomUtil.deceased();

    LocalDate deceasedOn = indicator == Deceased.Y ? RandomUtil.dateInPast() : null;

    patient.update(
        new PatientCommand.UpdateMortality(
            identifier.id(),
            RandomUtil.dateInPast(),
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

  public void withEthnicity(
      final PatientIdentifier identifier,
      final String ethnicity
  ) {
    Person patient = managed(identifier);

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            identifier.id(),
            RandomUtil.dateInPast(),
            ethnicity,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withSpecificEthnicity(
      final PatientIdentifier identifier,
      final String ethnicity,
      final String detail
  ) {
    Person patient = managed(identifier);

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            identifier.id(),
            RandomUtil.dateInPast(),
            ethnicity,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            identifier.id(),
            detail,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withEthnicity(final PatientIdentifier identifier) {
    withEthnicity(identifier, RandomUtil.ethnicity());
  }

  public void withStateHIVCase(final PatientIdentifier identifier, final String value) {
    jdbcClient.sql("update person set ehars_id = ?, as_of_date_general = GETDATE() where person_uid = ?")
        .params(value, identifier.id())
        .update();

  }
}
