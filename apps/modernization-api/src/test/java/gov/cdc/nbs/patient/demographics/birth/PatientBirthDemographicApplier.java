package gov.cdc.nbs.patient.demographics.birth;

import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class PatientBirthDemographicApplier {

  private final JdbcClient client;
  private final AddressIdentifierGenerator addressIdentifierGenerator;

  PatientBirthDemographicApplier(
      final JdbcClient client,
      final AddressIdentifierGenerator addressIdentifierGenerator
  ) {
    this.client = client;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
  }

  void withBirthday(
      final PatientIdentifier identifier,
      final LocalDate birthday
  ) {
    withBirthday(identifier, RandomUtil.dateInPast(), birthday);
  }

  void withBirthday(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final LocalDate birthday
  ) {

    client.sql(
            """
                update person set
                    as_of_date_sex = ?,
                    birth_time = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(birthday)
        .param(identifier.id())
        .update();

  }

  void withBornNth(
      final PatientIdentifier identifier,
      final int order
  ) {

    client.sql(
            """
                update person set
                    as_of_date_sex = coalesce(as_of_date_sex, getDate()),
                    birth_order_nbr = ?,
                    multiple_birth_ind = 'Y'
                where person_uid = ?
                """
        )
        .param(order)
        .param(identifier.id())
        .update();
  }

  void withSingleBirth(
      final PatientIdentifier identifier
  ) {

    client.sql(
            """
                update person set
                    as_of_date_sex = coalesce(as_of_date_sex, getDate()),
                    multiple_birth_ind = 'N'
                where person_uid = ?
                """
        )
        .param(identifier.id())
        .update();
  }

  void withBornAs(
      final PatientIdentifier identifier,
      final String gender
  ) {

    client.sql(
            """
                update person set
                    as_of_date_sex = coalesce(as_of_date_sex, getDate()),
                    birth_gender_cd = ?
                where person_uid = ?
                """
        )
        .param(gender)
        .param(identifier.id())
        .update();

  }

  void withBirthLocation(
      final PatientIdentifier identifier,
      final String city,
      final String county,
      final String state,
      final String country
  ) {
    withBirthLocation(identifier, LocalDate.now(), city, county, state, country);
  }

  void withBirthLocation(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String city,
      final String county,
      final String state,
      final String country
  ) {

    long locator = this.addressIdentifierGenerator.generate();
    client.sql(
            """
                -- Ensure that the birth as of date is present
                update person set
                    as_of_date_sex = coalesce(as_of_date_sex, getDate())
                where person_uid = :patient;
                
                --- Entity Participation
                merge into Entity_locator_participation [participation]
                using ( values (:patient)) as source (patient)
                on      [participation].entity_uid = source.patient
                    and [participation].use_cd = 'BIR'
                    and [participation].class_cd = 'PST'
                when  not matched then
                    insert (
                      version_ctrl_nbr,
                      entity_uid,
                      locator_uid,
                      add_time,
                      last_chg_time,
                      record_status_cd,
                      record_status_time,
                      as_of_date,
                      use_cd,
                      class_cd
                  ) values (
                      1,
                      source.patient,
                      :locator,
                      getDate(),
                      getDate(),
                      'ACTIVE',
                      getDate(),
                      :asOf,
                      'BIR',
                      'PST'
                  );
                
                --- Locator
                merge into Postal_locator [locator]
                using (
                    select
                        locator_uid as [id],
                        :city       as [city],
                        :state      as [state],
                        :county     as [county],
                        :country    as [country]
                    from Entity_locator_participation
                    where   entity_uid = :patient
                        and use_cd = 'BIR'
                        and class_cd = 'PST'
                ) as source
                on [locator].postal_locator_uid = [source].[id]
                when matched then
                    update set
                        city_desc_txt = coalesce([source].city, city_desc_txt),
                        cnty_cd = coalesce([source].county, cnty_cd),
                        state_cd = coalesce([source].state, state_cd),
                        cntry_cd = coalesce([source].country , cntry_cd)
                when not matched then
                    insert (
                      postal_locator_uid,
                      city_desc_txt,
                      cnty_cd,
                      state_cd,
                      cntry_cd,
                      add_time,
                      last_chg_time,
                      record_status_cd,
                      record_status_time
                  ) values (
                      [source].[id],
                      [source].city,
                      [source].county,
                      [source].state,
                      [source].country,
                      getDate(),
                      getDate(),
                      'ACTIVE',
                      getDate()
                  );
                """
        )
        .param("patient", identifier.id())
        .param("asOf", asOf)
        .param("locator", locator)
        .param("city", city)
        .param("county", county)
        .param("state", state)
        .param("country", country)
        .update();

  }

}
