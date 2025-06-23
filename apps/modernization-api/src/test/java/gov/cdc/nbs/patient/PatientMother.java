package gov.cdc.nbs.patient;

import gov.cdc.nbs.data.LimitString;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.support.IdentificationMother;
import gov.cdc.nbs.support.RaceMother;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@ScenarioScope
@Transactional
public class PatientMother {

  private static final String DELETE_IN = """  
      -- Remove the history
      delete from [locator]
      from [Tele_locator_hist] [locator]
          join [Entity_locator_participation] [participation] on
                  [locator].[tele_locator_uid] = [participation].[locator_uid]
      where [participation].entity_uid in (:identifiers);
      delete from [locator]
      from [Postal_locator_hist] [locator]
          join [Entity_locator_participation] [participation] on
                  [locator].[postal_locator_uid] = [participation].[locator_uid]
      where [participation].entity_uid in (:identifiers);
      
      delete from Entity_loc_participation_hist where entity_uid in (:identifiers);
      
      delete from Entity_id_hist where entity_uid in (:identifiers);
      
      delete from Person_race_hist where person_uid in (:identifiers);
      
      delete from Person_ethnic_group_hist where person_uid in (:identifiers);
      
      delete from Person_Name_hist where person_uid in (:identifiers);
      
      delete from person_hist where person_uid in (:identifiers);
      
      --  Remove the Patient
      
            delete from [locator]
      from [Tele_locator] [locator]
          join [Entity_locator_participation] [participation] on
                  [locator].[tele_locator_uid] = [participation].[locator_uid]
      where [participation].entity_uid in (:identifiers);
      
      delete from [locator]
      from [Postal_locator] [locator]
          join [Entity_locator_participation] [participation] on
                  [locator].[postal_locator_uid] = [participation].[locator_uid]
      where [participation].entity_uid in (:identifiers);
      
      delete from Entity_locator_participation where entity_uid in (:identifiers);
      
      delete from Entity_id where entity_uid in (:identifiers);
      
      delete from Person_race where person_uid in (:identifiers);
      
      delete from Person_ethnic_group where person_uid in (:identifiers);
      
      delete from Person_Name where person_uid in (:identifiers);
      
      delete from person where person_uid in (:identifiers);
      """;

  private final Faker faker;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final PatientLocalIdentifierGenerator localIdentifierGenerator;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final PhoneIdentifierGenerator phoneIdentifierGenerator;
  private final PatientShortIdentifierResolver shortIdentifierResolver;
  private final EntityManager entityManager;
  private final Available<PatientIdentifier> available;
  private final Active<PatientIdentifier> active;
  private final TestingDataCleaner<Long> cleaner;
  private final JdbcClient client;
  private final SoundexResolver soundexResolver;

  PatientMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final PatientLocalIdentifierGenerator localIdentifierGenerator,
      final AddressIdentifierGenerator addressIdentifierGenerator,
      final PhoneIdentifierGenerator phoneIdentifierGenerator,
      final PatientShortIdentifierResolver shortIdentifierResolver,
      final EntityManager entityManager,
      final Available<PatientIdentifier> available,
      final Active<PatientIdentifier> active,
      final JdbcClient client,
      final SoundexResolver soundexResolver
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.localIdentifierGenerator = localIdentifierGenerator;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.phoneIdentifierGenerator = phoneIdentifierGenerator;
    this.shortIdentifierResolver = shortIdentifierResolver;
    this.entityManager = entityManager;
    this.available = available;
    this.active = active;
    this.client = client;
    this.soundexResolver = soundexResolver;
    this.faker = new Faker(Locale.of("en-us"));

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  public PatientIdentifier create() {
    PatientIdentifier created = patient();
    available.available(created);
    active.active(created);
    this.cleaner.include(created.id());
    return created;
  }

  public PatientIdentifier available() {
    PatientIdentifier created = patient();
    available.available(created);
    this.cleaner.include(created.id());
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
            settings.createdOn()));

    this.entityManager.persist(patient);

    long shortId = this.shortIdentifierResolver.resolve(local).orElse(0L);

    return new PatientIdentifier(patient.getId(), shortId, local);
  }

  private Person managed(final PatientIdentifier identifier) {
    return this.entityManager.find(Person.class, identifier.id());
  }

  public void deleted(final PatientIdentifier identifier) {
    Person patient = managed(identifier);

    patient.delete(
        new PatientCommand.Delete(
            identifier.id(),
            this.settings.createdBy(),
            this.settings.createdOn()),
        id -> 0);
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
      final String zip) {
    withAddress(identifier, "H", address, city, county, state, zip);
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip) {
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
      final String zip,
      final LocalDate asOf) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddAddress(
            patient.getId(),
            asOf,
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
        ),
        addressIdentifierGenerator
    );
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddAddress(
            patient.getId(),
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
        ),
        addressIdentifierGenerator
    );
  }

  public void withAddress(final PatientIdentifier identifier) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddAddress(
            patient.getId(),
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
        ),
        addressIdentifierGenerator
    );
  }

  public void withIdentification(final PatientIdentifier identifier) {
    withIdentification(
        identifier,
        RandomUtil.getRandomFromArray(IdentificationMother.IDENTIFICATION_CODE_LIST),
        RandomUtil.getRandomNumericString(8));
  }

  public void withIdentification(
      final PatientIdentifier identifier,
      final String type,
      final String value) {

    withIdentification(
        identifier,
        type,
        value,
        RandomUtil.dateInPast());

  }

  public void withIdentification(
      final PatientIdentifier identifier,
      final String type,
      final String value,
      final LocalDate asOf) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddIdentification(
            identifier.id(),
            asOf,
            value,
            RandomUtil.maybeOneFrom("GA"),
            type,
            this.settings.createdBy(),
            this.settings.createdOn()));
  }

  public void withRace(final PatientIdentifier identifier) {
    withRace(identifier, RandomUtil.getRandomFromArray(RaceMother.RACE_LIST));
  }

  public void withRace(
      final PatientIdentifier identifier,
      final String race
  ) {
    withRace(identifier, RandomUtil.dateInPast(), race);
  }

  public void withRace(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String race
  ) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddRace(
            identifier.id(),
            asOf,
            race,
            this.settings.createdBy(),
            this.settings.createdOn()
        )
    );
  }

  public void withRaceIncluding(
      final PatientIdentifier identifier,
      final String race,
      final String detail
  ) {
    Person patient = managed(identifier);

    LocalDate asOf = patient.getRace().races()
        .stream()
        .filter(r -> Objects.equals(r.getRaceCategoryCd(), race) && Objects.equals(r.getRaceCd(), race))
        .findFirst()
        .map(PersonRace::getAsOfDate)
        .orElseThrow();

    List<String> details =
        Stream.concat(
                //  any existing details for the race category
                patient.getRace().races()
                    .stream()
                    .filter(
                        existing -> !Objects.equals(existing.getRaceCategoryCd(), existing.getRaceCd())
                            && Objects.equals(existing.getRaceCategoryCd(), race)
                    )
                    .map(PersonRace::getRaceCd),
                //  the new detail
                Stream.of(detail)
            )
            .toList();



    patient.update(
        new PatientCommand.UpdateRaceInfo(
            identifier.id(),
            asOf,
            race,
            details,
            this.settings.createdBy(),
            this.settings.createdOn()));
  }

  public void withName(final PatientIdentifier identifier) {
    withName(
        identifier,
        RandomUtil.dateInPast(),
        "L",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        null);
  }

  public void withName(
      final PatientIdentifier identifier,
      final String type,
      final String first,
      final String last) {
    withName(
        identifier,
        RandomUtil.dateInPast(),
        type,
        first,
        last);
  }

  public void withName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String type,
      final String first,
      final String last) {
    withName(
        identifier,
        asOf,
        type,
        first,
        null,
        last,
        null);
  }

  public void withName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix) {
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
            this.settings.createdOn()));
  }

  public void withPhone(final PatientIdentifier identifier) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddPhone(
            identifier.id(),
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
        ),
        phoneIdentifierGenerator
    );
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String number) {
    withPhone(identifier, null, number, null);
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String countryCode,
      final String number,
      final String extension) {
    withPhone(identifier, "PH", "H", countryCode, number, extension);
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String countryCode,
      final String number,
      final String extension) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddPhone(
            identifier.id(),
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
        ),
        phoneIdentifierGenerator
    );
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String countryCode,
      final String number,
      final String extension,
      final LocalDate date) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddPhone(
            identifier.id(),
            type,
            use,
            date,
            countryCode,
            number,
            extension,
            null,
            null,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()
        ),
        phoneIdentifierGenerator
    );
  }

  public void withEmail(final PatientIdentifier identifier) {
    withEmail(identifier, LimitString.toMaxLength(faker.internet().emailAddress(), 100));
  }

  public void withEmail(final PatientIdentifier identifier, final String email) {
    withEmail(
        identifier,
        "NET",
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        email,
        RandomUtil.dateInPast());
  }

  public void withEmail(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String email,
      final LocalDate asOf) {
    Person patient = managed(identifier);

    patient.add(
        new PatientCommand.AddPhone(
            identifier.id(),
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
        ),
        phoneIdentifierGenerator
    );
  }

  public void withEmail(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String email) {
    withEmail(
        identifier,
        type,
        use,
        email,
        RandomUtil.dateInPast());
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
            this.settings.createdOn()),
        this.addressIdentifierGenerator);
  }

  public void withGender(final PatientIdentifier identifier) {
    Person patient = managed(identifier);

    patient.update(
        new PatientCommand.UpdateGender(
            identifier.id(),
            RandomUtil.dateInPast(),
            RandomUtil.gender().value(),
            null,
            null,
            null,
            this.settings.createdBy(),
            this.settings.createdOn()));
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
            this.settings.createdOn()),
        this.addressIdentifierGenerator);
  }

  public void withEthnicity(
      final PatientIdentifier identifier,
      final String ethnicity,
      final LocalDate asOf
  ) {
    client.sql(
            """
                update person set
                    ethnic_group_ind = ?,
                    as_of_date_ethnicity = ?
                where person_uid = ?
                """
        )
        .param(ethnicity)
        .param(asOf)
        .param(identifier.id())
        .update();
  }

  public void withEthnicity(
      final PatientIdentifier identifier,
      final String ethnicity
  ) {
    withEthnicity(identifier, ethnicity, RandomUtil.dateInPast());
  }

  public void withEthnicity(final PatientIdentifier identifier) {
    withEthnicity(identifier, RandomUtil.ethnicity());
  }


  public void withSpecificEthnicity(
      final PatientIdentifier identifier,
      final String detail
  ) {
    client.sql(
            """
                insert into Person_ethnic_group(
                    person_uid,
                    ethnic_group_cd,
                    add_time,
                    add_user_id,
                    record_status_cd
                ) values (
                    :patient,
                    :detail,
                    :addedOn,
                    :addedBy,
                    'ACTIVE'
                )
                """
        )
        .param("patient", identifier.id())
        .param("detail", detail)
        .param("addedOn", this.settings.createdOn())
        .param("addedBy", this.settings.createdBy())
        .update();
  }

  public void withUnknownEthnicity(
      final PatientIdentifier identifier,
      final String reason
  ) {
    withUnknownEthnicity(identifier, reason, RandomUtil.dateInPast());
  }

  public void withUnknownEthnicity(
      final PatientIdentifier identifier,
      final String reason,
      final LocalDate asOf
  ) {
    client.sql(
            """
                update person set
                    ethnic_group_ind = 'UNK',
                    ethnic_unk_reason_cd = ?,
                    as_of_date_ethnicity = ?
                where person_uid = ?
                """
        )
        .param(reason)
        .param(asOf)
        .param(identifier.id())
        .update();
  }


  public void withAsOf(final PatientIdentifier identifier, final LocalDate value) {
    client.sql("update person set as_of_date_admin = ? where person_uid = ?")
        .param(value)
        .param(identifier.id())
        .update();
  }

  public void withComment(final PatientIdentifier identifier, final String value) {
    client.sql("update person set [description] = ? where person_uid = ?")
        .param(value)
        .param(identifier.id())
        .update();
  }
}
