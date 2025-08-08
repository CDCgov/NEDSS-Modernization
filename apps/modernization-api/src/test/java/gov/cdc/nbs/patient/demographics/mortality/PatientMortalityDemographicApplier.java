package gov.cdc.nbs.patient.demographics.mortality;

import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class PatientMortalityDemographicApplier {

  private final JdbcClient client;
  private final AddressIdentifierGenerator addressIdentifierGenerator;

  PatientMortalityDemographicApplier(
      final JdbcClient client,
      final AddressIdentifierGenerator addressIdentifierGenerator
  ) {
    this.client = client;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
  }

  void withDeceasedOn(
      final PatientIdentifier identifier,
      final LocalDate on
  ) {
    withDeceasedOn(identifier, RandomUtil.dateInPast(), on);
  }

  void withDeceasedOn(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final LocalDate on
  ) {

    client.sql(
            """
                update person set
                    as_of_date_morbidity = coalesce(?, getDate()),
                    deceased_ind_cd = 'Y',
                    deceased_time = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(on)
        .param(identifier.id())
        .update();

  }

  void withDeceased(final PatientIdentifier identifier, final String value) {
    client.sql(
            """
                update person set
                    as_of_date_morbidity = coalesce(as_of_date_morbidity, getDate()),
                    deceased_ind_cd = ?
                where person_uid = ?
                """
        )
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withLocation(
      final PatientIdentifier identifier,
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
                    as_of_date_morbidity = coalesce(as_of_date_morbidity, getDate())
                where person_uid = :patient;
                
                --- Entity Participation
                merge into Entity_locator_participation [participation]
                using ( values (:patient)) as source (patient)
                on      [participation].entity_uid = source.patient
                    and [participation].use_cd = 'DTH'
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
                      getDate(),
                      'DTH',
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
                        and use_cd = 'DTH'
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
        .param("locator", locator)
        .param("city", city)
        .param("county", county)
        .param("state", state)
        .param("country", country)
        .update();

  }

}
