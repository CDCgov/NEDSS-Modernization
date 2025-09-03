package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.demographics.identification.PatientIdentificationDemographicApplier;
import gov.cdc.nbs.patient.demographics.phone.PatientEmailDemographicApplier;
import gov.cdc.nbs.patient.demographics.phone.PatientPhoneDemographicApplier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
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
import java.util.Locale;

@Component
@ScenarioScope
@Transactional
public class PatientMother {

  private static final String CREATE = """
      insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
      
      insert into Person(
          person_uid,
          person_parent_uid,
          local_id,
          version_ctrl_nbr,
          cd,
          electronic_ind,
          edx_ind,
          add_time,
          add_user_id,
          last_chg_time,
          last_chg_user_id,
          record_status_cd,
          record_status_time,
          status_cd,
          status_time
      ) values (
          :identifier,
          :identifier,
          :local,
          1,
          'PAT',
          'N',
          'Y',
          :addedOn,
          :addedBy,
          :addedOn,
          :addedBy,
          'ACTIVE',
          :addedOn,
          'A',
          :addedOn
      );
      """;

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
      
      -- Remove any revisions
      declare @revisions table (
          person_uid bigint not null
      );
      
      insert into @revisions
      select
          person_uid
      from [Person]
      where person_parent_uid in (:identifiers)
        and person_parent_uid <> person_uid;
      
      delete [names] from @revisions [revision]
          join  person_name [names] on
                  [names].[person_uid] = [revision].[person_uid];
      
      delete Person from @revisions [revision]
          join  Person on
                  Person.[person_uid] = [revision].[person_uid];
      
      delete Participation from @revisions [revision]
          join  Participation on
                  [subject_entity_uid] = [revision].[person_uid]
              and [subject_class_cd] = 'PSN';
      
      delete Entity from @revisions [revision]
        join  Entity on
                  [entity_uid] = [revision].[person_uid]
              and [class_cd] = 'PSN';
      
      --  Remove the Patient
      delete from Participation where subject_class_cd = 'PSN' and subject_entity_uid in (:identifiers);
      
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
      
      delete from entity where [class_cd] = 'PSN' and entity_uid in (:identifiers);
      """;

  private final Faker faker;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final PatientLocalIdentifierGenerator localIdentifierGenerator;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final PatientShortIdentifierResolver shortIdentifierResolver;
  private final EntityManager entityManager;
  private final Available<PatientIdentifier> available;
  private final Active<PatientIdentifier> active;
  private final TestingDataCleaner<Long> cleaner;
  private final JdbcClient client;
  private final PatientEmailDemographicApplier emailDemographicApplier;
  private final PatientPhoneDemographicApplier phoneDemographicApplier;
  private final PatientIdentificationDemographicApplier identificationDemographicApplier;
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
      final JdbcClient client,
      final PatientEmailDemographicApplier emailDemographicApplier,
      final PatientPhoneDemographicApplier phoneDemographicApplier,
      final PatientIdentificationDemographicApplier identificationDemographicApplier,
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
    this.client = client;
    this.emailDemographicApplier = emailDemographicApplier;
    this.phoneDemographicApplier = phoneDemographicApplier;
    this.identificationDemographicApplier = identificationDemographicApplier;
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

    this.client.sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("addedOn", this.settings.createdOn())
        .param("addedBy", this.settings.createdBy())
        .update();

    long shortId = this.shortIdentifierResolver.resolve(local).orElse(0L);

    return new PatientIdentifier(identifier, shortId, local);
  }

  private Person managed(final PatientIdentifier identifier) {
    return this.entityManager.find(Person.class, identifier.id());
  }

  public void deleted(final PatientIdentifier identifier) {
    withStatus(identifier, "LOG_DEL", LocalDateTime.now());
  }

  public void superseded(final PatientIdentifier identifier) {
    withStatus(identifier, "SUPERCEDED", LocalDateTime.now());
  }

  private void withStatus(
      final PatientIdentifier patient,
      final String status,
      final LocalDateTime when
  ) {
    this.client.sql("update Person set record_status_cd = ?, record_status_time = ? where person_uid = ?")
        .param(status)
        .param(when)
        .param(patient.id())
        .update();
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
            patient.id(),
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
            patient.id(),
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
            patient.id(),
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
    identificationDemographicApplier.withIdentification(identifier);
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
    phoneDemographicApplier.withPhone(identifier);
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
    phoneDemographicApplier.withPhone(identifier, "PH", "H", countryCode, number, extension, RandomUtil.dateInPast());
  }

  public void withEmail(final PatientIdentifier identifier) {
    emailDemographicApplier.withEmail(identifier);
  }

  public void withEmail(final PatientIdentifier identifier, final String email) {
    emailDemographicApplier.withEmail(
        identifier,
        "NET",
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        email,
        RandomUtil.dateInPast());
  }

  public void withBirthInformation(final PatientIdentifier identifier) {

    client.sql("""
            update Person set
                              as_of_date_sex = :asOf,
                              birth_time = :birthday,
                              birth_gender_cd = :gender,
                              multiple_birth_ind = :multiple
                          where person_uid = :patient
            """)
        .param("patient", identifier.id())
        .param("asOf", RandomUtil.dateInPast())
        .param("birthday", RandomUtil.dateInPast())
        .param("gender", RandomUtil.maybeGender())
        .param("multiple", RandomUtil.maybeIndicator())
        .update();
  }

  public void withGender(final PatientIdentifier identifier) {
    client.sql("update Person set as_of_date_sex = :asOf, curr_sex_cd = :gender where person_uid = :patient")
        .param("patient", identifier.id())
        .param("asOf", RandomUtil.dateInPast())
        .param("gender", RandomUtil.gender())
        .update();
  }

  public void withLocalId(final PatientIdentifier patient, final String localId) {
    client.sql("update Person set local_id = ? where person_uid = ?")
        .param(localId)
        .param(patient.id())
        .update();
  }

  public void withMortality(final PatientIdentifier identifier) {

    String indicator = RandomUtil.indicator();

    LocalDate deceasedOn = "Y".equals(indicator) ? RandomUtil.dateInPast() : null;

    this.client.sql("update Person set deceased_time = ?, deceased_ind_cd = ? where person_uid = ?")
        .param(deceasedOn)
        .param(indicator)
        .param(identifier.id())
        .update();
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

  public void withRace(final PatientIdentifier patient) {
    withRace(patient, RandomUtil.oneFrom("2106-3", "2054-5", "2028-9", "U"));
  }

  public void withRace(final PatientIdentifier patient, final String race) {
    this.client.sql(
            """
                insert into Person_race (
                    person_uid,
                    as_of_date,
                    race_cd,
                    race_category_cd,
                    add_user_id,
                    add_time,
                    last_chg_user_id,
                    last_chg_time,
                    record_status_cd,
                    record_status_time
                ) values (
                    :patient,
                    :asOf,
                    :race,
                    :race,
                    :addedBy,
                    :addedOn,
                    :addedBy,
                    :addedOn,
                    'ACTIVE',
                    :addedOn
                );
                """
        ).param("patient", patient.id())
        .param("asOf", RandomUtil.dateInPast())
        .param("race", race)
        .param("addedBy", settings.createdBy())
        .param("addedOn", settings.createdOn())
        .update();
  }
}
