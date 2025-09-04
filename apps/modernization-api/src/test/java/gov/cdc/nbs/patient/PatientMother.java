package gov.cdc.nbs.patient;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.demographics.address.PatientAddressDemographicApplier;
import gov.cdc.nbs.patient.demographics.birth.PatientBirthDemographicApplier;
import gov.cdc.nbs.patient.demographics.ethnicity.PatientEthnicityDemographicApplier;
import gov.cdc.nbs.patient.demographics.gender.PatientGenderApplier;
import gov.cdc.nbs.patient.demographics.identification.PatientIdentificationDemographicApplier;
import gov.cdc.nbs.patient.demographics.mortality.PatientMortalityDemographicApplier;
import gov.cdc.nbs.patient.demographics.name.PatientNameDemographicApplier;
import gov.cdc.nbs.patient.demographics.phone.PatientEmailDemographicApplier;
import gov.cdc.nbs.patient.demographics.phone.PatientPhoneDemographicApplier;
import gov.cdc.nbs.patient.demographics.race.PatientRaceDemographicApplier;
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
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static gov.cdc.nbs.support.util.RandomUtil.oneFrom;

@Component
@ScenarioScope
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

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final PatientLocalIdentifierGenerator localIdentifierGenerator;
  private final PatientShortIdentifierResolver shortIdentifierResolver;
  private final Available<PatientIdentifier> available;
  private final Active<PatientIdentifier> active;
  private final TestingDataCleaner<Long> cleaner;
  private final JdbcClient client;
  private final PatientEmailDemographicApplier emailDemographicApplier;
  private final PatientPhoneDemographicApplier phoneDemographicApplier;
  private final PatientIdentificationDemographicApplier identificationDemographicApplier;
  private final PatientNameDemographicApplier nameDemographicApplier;
  private final PatientAddressDemographicApplier addressDemographicApplier;
  private final PatientRaceDemographicApplier raceDemographicApplier;
  private final PatientGenderApplier genderDemographicApplier;
  private final PatientEthnicityDemographicApplier ethnicityDemographicApplier;
  private final PatientBirthDemographicApplier birthDemographicApplier;
  private final PatientMortalityDemographicApplier mortalityDemographicApplier;

  PatientMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final PatientLocalIdentifierGenerator localIdentifierGenerator,
      final PatientShortIdentifierResolver shortIdentifierResolver,
      final Available<PatientIdentifier> available,
      final Active<PatientIdentifier> active,
      final JdbcClient client,
      final PatientEmailDemographicApplier emailDemographicApplier,
      final PatientPhoneDemographicApplier phoneDemographicApplier,
      final PatientIdentificationDemographicApplier identificationDemographicApplier,
      final PatientNameDemographicApplier nameDemographicApplier,
      final PatientAddressDemographicApplier addressDemographicApplier,
      final PatientRaceDemographicApplier raceDemographicApplier,
      final PatientGenderApplier genderDemographicApplier,
      final PatientEthnicityDemographicApplier ethnicityDemographicApplier,
      final PatientBirthDemographicApplier birthDemographicApplier,
      final PatientMortalityDemographicApplier mortalityDemographicApplier
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.localIdentifierGenerator = localIdentifierGenerator;
    this.shortIdentifierResolver = shortIdentifierResolver;
    this.available = available;
    this.active = active;
    this.client = client;
    this.emailDemographicApplier = emailDemographicApplier;
    this.phoneDemographicApplier = phoneDemographicApplier;
    this.identificationDemographicApplier = identificationDemographicApplier;
    this.nameDemographicApplier = nameDemographicApplier;
    this.addressDemographicApplier = addressDemographicApplier;
    this.raceDemographicApplier = raceDemographicApplier;
    this.genderDemographicApplier = genderDemographicApplier;
    this.ethnicityDemographicApplier = ethnicityDemographicApplier;
    this.birthDemographicApplier = birthDemographicApplier;
    this.mortalityDemographicApplier = mortalityDemographicApplier;

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

  public void withAddress(final PatientIdentifier identifier) {
    addressDemographicApplier.withAddress(identifier);
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip
  ) {
    addressDemographicApplier.withAddress(identifier, "H", address, city, county, state, zip);
  }



  public void withIdentification(final PatientIdentifier identifier) {
    identificationDemographicApplier.withIdentification(identifier);
  }

  public void withName(final PatientIdentifier identifier) {
    this.nameDemographicApplier.withName(identifier);
  }

  public void withName(
      final PatientIdentifier identifier,
      final String type,
      final String first,
      final String last
  ) {
    this.nameDemographicApplier.withName(identifier, type, first, last);
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
        oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        email,
        RandomUtil.dateInPast());
  }

  public void withBirthInformation(final PatientIdentifier identifier) {
    birthDemographicApplier.withBirthInformation(identifier);
  }

  public void withGender(final PatientIdentifier identifier) {
    genderDemographicApplier.withGender(identifier);
  }

  public void withLocalId(final PatientIdentifier patient, final String localId) {
    client.sql("update Person set local_id = ? where person_uid = ?")
        .param(localId)
        .param(patient.id())
        .update();
  }

  public void withMortality(final PatientIdentifier identifier) {
    mortalityDemographicApplier.withMortality(identifier);
  }


  public void withEthnicity(final PatientIdentifier identifier) {
    this.ethnicityDemographicApplier.withEthnicity(identifier);
  }


  public void withRace(final PatientIdentifier patient) {
    raceDemographicApplier.withRace(patient);
  }

  public void withRace(final PatientIdentifier patient, final String race) {
    raceDemographicApplier.withRace(patient, race);
  }
}
